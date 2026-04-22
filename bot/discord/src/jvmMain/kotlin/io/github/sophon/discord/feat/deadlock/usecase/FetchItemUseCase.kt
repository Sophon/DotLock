package io.github.sophon.discord.feat.deadlock.usecase

import io.github.sophon.core.arch.Result
import io.github.sophon.core.arch.map
import io.github.sophon.core.arch.mapError
import io.github.sophon.core.domain.model.Item
import io.github.sophon.deadlock.DeadlockFeatureInfo
import io.github.sophon.deadlock.DeadlockWikiClient
import io.github.sophon.discord.EMBED_BUTTON_DURATION_INF
import io.github.sophon.discord.data.toDomain
import io.github.sophon.discord.domain.model.BotError
import io.github.sophon.discord.domain.model.BotOutput
import io.github.sophon.discord.domain.model.Command
import io.github.sophon.discord.feat.emoji.Emojifier
import io.github.sophon.discord.ui.itemEmbed
import kotlin.time.Duration.Companion.seconds

internal class FetchItemUseCase(
    private val wikiClient: DeadlockWikiClient,
    private val emojifier: Emojifier,
) {
    suspend fun invoke(itemName: String): Result<BotOutput, BotError> {
        return wikiClient.fetchItem(itemName)
            .map { it.toBotOutput() }
            .mapError { it.toDomain(itemName) }
    }

    private fun Item.toBotOutput(): BotOutput {
        val output = BotOutput(
            primaryEmbedBuilder = itemEmbed(
                item = this,
                featureInfo = DeadlockFeatureInfo.featureInfo,
                emojifier = emojifier,
            ),
            buttons = upgradePath.formUpgradeButtons(),
        )
        return output
    }

    private fun Item.UpgradePath.formUpgradeButtons(): BotOutput.ButtonSet {
        var index = 1
        val buttonList = buildList {
            (from + to).forEach { item ->
                val query = "${Command.Item.name} $item"
                add(
                    BotOutput.EmbedButton(
                        label = "${index++}",
                        action = BotOutput.EmbedButton.Action.Query(query)
                    )
                )
            }
        }
        val buttonSet = BotOutput.ButtonSet(
            buttonList = buttonList,
            duration = EMBED_BUTTON_DURATION_INF.seconds,
        )

        return buttonSet
    }
}
