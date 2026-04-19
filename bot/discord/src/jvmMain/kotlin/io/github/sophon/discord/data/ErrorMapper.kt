package io.github.sophon.discord.data

import io.github.sophon.core.arch.WikiError
import io.github.sophon.discord.domain.model.BotError

internal fun WikiError.toDomain(input: String): BotError {
    return when (this) {
        is WikiError.NetworkError -> BotError.DownloadError(this.error)
        is WikiError.ParseError -> BotError.Unknown(input)
        is WikiError.NotFound -> BotError.NotFound(this.error)
        else -> BotError.Unknown(this.error, input)
    }
}