package io.github.sophon.discord.usecase

import io.github.sophon.core.arch.Result
import io.github.sophon.core.arch.map
import io.github.sophon.core.arch.mapError
import io.github.sophon.core.domain.model.Item
import io.github.sophon.deadlock.DeadlockFeatureInfo
import io.github.sophon.deadlock.DeadlockWikiClient
import io.github.sophon.discord.data.toDomain
import io.github.sophon.discord.domain.BotError
import io.github.sophon.discord.domain.BotOutput
import io.github.sophon.discord.feat.emoji.Emojifier
import io.github.sophon.discord.ui.itemEmbed

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
            )
        )
        return output
    }
}
