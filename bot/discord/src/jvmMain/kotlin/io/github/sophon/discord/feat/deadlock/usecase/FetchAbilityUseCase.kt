package io.github.sophon.discord.feat.deadlock.usecase

import io.github.sophon.core.arch.Result
import io.github.sophon.core.arch.map
import io.github.sophon.core.arch.mapError
import io.github.sophon.core.domain.model.Ability
import io.github.sophon.deadlock.DeadlockFeatureInfo
import io.github.sophon.deadlock.DeadlockWikiClient
import io.github.sophon.discord.data.toDomain
import io.github.sophon.discord.domain.model.BotError
import io.github.sophon.discord.domain.model.BotOutput
import io.github.sophon.discord.feat.emoji.Emojifier
import io.github.sophon.discord.ui.abilityEmbed

internal class FetchAbilityUseCase(
    private val wikiClient: DeadlockWikiClient,
    private val emojifier: Emojifier,
) {
    suspend fun invoke(abilityName: String): Result<BotOutput, BotError> {
        return wikiClient.fetchAbility(abilityName)
            .map { it.toBotOutput() }
            .mapError { it.toDomain(abilityName) }
    }


    private fun Ability.toBotOutput(): BotOutput {
        val output = BotOutput(
            primaryEmbedBuilder = abilityEmbed(
                ability = this,
                featureInfo = DeadlockFeatureInfo.featureInfo,
                emojifier = emojifier,
            )
        )
        return output
    }
}