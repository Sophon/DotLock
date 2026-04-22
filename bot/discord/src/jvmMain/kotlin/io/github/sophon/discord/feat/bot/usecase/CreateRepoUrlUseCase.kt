package io.github.sophon.discord.feat.bot.usecase

import io.github.sophon.core.arch.Result
import io.github.sophon.discord.URL_REPO
import io.github.sophon.discord.domain.model.BotError
import io.github.sophon.discord.domain.model.BotOutput

internal class CreateRepoUrlUseCase {
    fun invoke(): Result<BotOutput, BotError> {
        val output = BotOutput(
            plainText = "Contribute to FireFrog! $URL_REPO"
        )

        return Result.Success(output)
    }
}
