package io.github.sophon.discord.domain

import io.github.sophon.core.arch.Result
import io.github.sophon.core.domain.model.FeatureInfo
import io.github.sophon.core.domain.model.Game

internal interface DiscordRegisteredFeature {
    val featureInfo: FeatureInfo

    val supportedCommands: Set<Command>

    fun registerGames(enabledGames: List<Game>) {
        //default: for when feature doesn't support games
    }

    suspend fun start()

    suspend fun execute(
        command: Command,
        query: String,
        origin: Source,
    ): Result<BotOutput, BotError>
}
