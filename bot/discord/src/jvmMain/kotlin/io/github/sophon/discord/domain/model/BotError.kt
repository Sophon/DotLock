package io.github.sophon.discord.domain.model

import io.github.sophon.core.arch.Error

sealed class BotError(private vararg val inputs: String) : Error {
    class InvalidCommand(command: String) : BotError(command)
    class NotFound(input: String) : BotError(input)
    class BadUsage(query: String) : BotError(query)

    class DownloadError(error: String) : BotError(error)
    class BotLogicError(vararg inputs: String) : BotError(*inputs)
    class Kord(error: String): BotError(error)

    class FileError(vararg errors: String) : BotError(*errors)
    class DatabaseError : BotError()

    class Unknown(vararg errors: String) : BotError(*errors)

    override fun toString(): String =
        "${this::class.simpleName}(${inputs.joinToString()})"
}
