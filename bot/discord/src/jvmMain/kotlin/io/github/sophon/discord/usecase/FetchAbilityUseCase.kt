package io.github.sophon.discord.usecase

import io.github.sophon.core.arch.Result
import io.github.sophon.core.arch.map
import io.github.sophon.core.arch.mapError
import io.github.sophon.core.domain.model.Ability
import io.github.sophon.deadlock.DeadlockFeatureInfo
import io.github.sophon.deadlock.DeadlockWikiClient
import io.github.sophon.discord.data.toDomain
import io.github.sophon.discord.domain.BotError
import io.github.sophon.discord.domain.BotOutput
import io.github.sophon.discord.ui.abilityEmbed
import io.github.sophon.discord.ui.heroEmbed

internal class FetchAbilityUseCase(
    private val wikiClient: DeadlockWikiClient,
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
            )
        )
        return output
    }
}