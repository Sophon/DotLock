package io.github.sophon.discord.feat.deadlock

import io.github.aakira.napier.Napier
import io.github.sophon.core.arch.Result
import io.github.sophon.core.domain.model.FeatureInfo
import io.github.sophon.deadlock.DeadlockFeatureInfo
import io.github.sophon.deadlock.DeadlockWikiClient
import io.github.sophon.discord.domain.model.BotError
import io.github.sophon.discord.domain.model.BotOutput
import io.github.sophon.discord.domain.model.Command
import io.github.sophon.discord.domain.model.Source
import io.github.sophon.discord.feat.DiscordRegisteredFeature
import io.github.sophon.discord.feat.deadlock.usecase.FetchAbilityUseCase
import io.github.sophon.discord.feat.deadlock.usecase.FetchHeroUseCase
import io.github.sophon.discord.feat.deadlock.usecase.FetchItemUseCase

internal class DeadlockFeature(
    deadlockFeatureInfo: DeadlockFeatureInfo,
    private val wikiClient: DeadlockWikiClient,
    private val fetchItemUseCase: FetchItemUseCase,
    private val fetchHeroUseCase: FetchHeroUseCase,
    private val fetchAbilityUseCase: FetchAbilityUseCase,
): DiscordRegisteredFeature {
    override val featureInfo: FeatureInfo = deadlockFeatureInfo.featureInfo
    override val supportedCommands = setOf(
        Command.Item,
        Command.Hero,
        Command.Ability,
    )

    override suspend fun start() {
        Napier.d(tag = TAG) { "Starting: $featureInfo" }

        wikiClient.downloadAllData()
    }

    override suspend fun execute(
        command: Command,
        query: String?,
        origin: Source,
    ): Result<BotOutput, BotError> {
        if (query == null) {
            return Result.Error(BotError.BadUsage("missing query"))
        }

        val result = when (command) {
            Command.Item -> fetchItemUseCase.invoke(query)
            Command.Hero -> fetchHeroUseCase.invoke(query)
            Command.Ability -> fetchAbilityUseCase.invoke(query)
            else -> Result.Error(BotError.InvalidCommand(command.name))
        }

        return result
    }


    private companion object {
        const val TAG = "DeadlockFeature"
    }
}