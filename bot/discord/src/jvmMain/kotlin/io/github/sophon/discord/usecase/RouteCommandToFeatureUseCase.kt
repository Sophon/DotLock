package io.github.sophon.discord.usecase

import io.github.sophon.core.arch.Result
import io.github.sophon.core.util.extractFirstWord
import io.github.sophon.core.util.formKey
import io.github.sophon.core.util.normalizeWhiteSpace
import io.github.sophon.core.util.removeTag
import io.github.sophon.discord.domain.model.BotError
import io.github.sophon.discord.domain.model.BotOutput
import io.github.sophon.discord.domain.model.Command
import io.github.sophon.discord.domain.model.Command.Companion.fromStringOrNull
import io.github.sophon.discord.feat.DiscordRegisteredFeature
import io.github.sophon.discord.domain.model.Source

internal class RouteCommandToFeatureUseCase(
    private val featureList: List<DiscordRegisteredFeature>,
) {
    suspend fun invoke(
        command: String,
        query: String,
        source: Source,
    ): Result<BotOutput, BotError> {
        return executeCommand(
            commandString = command,
            query = query,
            source = source,
        )
    }

    suspend fun invoke(
        message: String,
        source: Source,
    ): Result<BotOutput, BotError> {
        val fullQuery = message
            .removeTag()
            .trim()
            .normalizeWhiteSpace()
            .takeIf { it.isNotBlank() }
            ?: return Result.Error(BotError.BadUsage(message))
        val commandString = fullQuery.extractFirstWord()
        val query = fullQuery.substringAfter(' ', missingDelimiterValue = "")
            .takeIf { it.isNotBlank() }

        return executeCommand(
            commandString = commandString,
            query = query,
            source = source,
        )
    }


    private suspend fun executeCommand(
        commandString: String,
        query: String?,
        source: Source,
    ): Result<BotOutput, BotError> {
        val command = Command.fromStringOrNull(commandString)
            ?: return Result.Error(BotError.InvalidCommand(commandString))
        val formattedQuery = query?.formKey()

        for (feature in featureList) {
            if (command !in feature.supportedCommands) continue
            val result = feature.execute(command = command, query = formattedQuery, origin = source)
            if (result is Result.Success) return result
        }

        return Result.Error(BotError.BotLogicError(commandString, query ?: ""))
    }


    private companion object {
        const val TAG = "RouteCommandToFeatureUseCase"
    }
}
