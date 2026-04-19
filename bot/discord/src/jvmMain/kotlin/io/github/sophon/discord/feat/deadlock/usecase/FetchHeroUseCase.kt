package io.github.sophon.discord.feat.deadlock.usecase

import io.github.sophon.core.arch.Result
import io.github.sophon.core.arch.map
import io.github.sophon.core.arch.mapError
import io.github.sophon.core.domain.model.Hero
import io.github.sophon.deadlock.DeadlockFeatureInfo
import io.github.sophon.deadlock.DeadlockWikiClient
import io.github.sophon.discord.EMBED_BUTTON_DURATION_INF
import io.github.sophon.discord.data.toDomain
import io.github.sophon.discord.domain.model.BotError
import io.github.sophon.discord.domain.model.BotOutput
import io.github.sophon.discord.domain.model.Command
import io.github.sophon.discord.feat.emoji.Emojifier
import io.github.sophon.discord.ui.heroEmbed
import kotlin.time.Duration.Companion.seconds

internal class FetchHeroUseCase(
    private val wikiClient: DeadlockWikiClient,
    private val emojifier: Emojifier,
) {
    suspend fun invoke(heroName: String): Result<BotOutput, BotError> {
        return wikiClient.fetchHero(heroName)
            .map { it.toBotOutput() }
            .mapError { it.toDomain(heroName) }
    }

    private fun Hero.toBotOutput(): BotOutput {
        val output = BotOutput(
            primaryEmbedBuilder = heroEmbed(
                hero = this,
                featureInfo = DeadlockFeatureInfo.featureInfo,
                emojifier = emojifier,
            ),
            buttons = formAbilityButtons(),
        )
        return output
    }

    private fun Hero.formAbilityButtons(): BotOutput.ButtonSet {
        val buttonList = boundAbilities
            .map { ability ->
                val query = "${Command.Ability.name} ${ability.name}"
                BotOutput.EmbedButton(
                    label = ability.slot.toString(),
                    action = BotOutput.EmbedButton.Action.Query(query),
                )
            }
            .toList()
        val buttonSet = BotOutput.ButtonSet(
            buttonList = buttonList,
            duration = EMBED_BUTTON_DURATION_INF.seconds,
        )

        return buttonSet
    }
}