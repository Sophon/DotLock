package io.github.sophon.discord.feat.bot.usecase

import io.github.sophon.core.arch.Result
import io.github.sophon.discord.URL_INVITATION
import io.github.sophon.discord.domain.model.BotError
import io.github.sophon.discord.domain.model.BotOutput

internal class CreateInvitationUseCase {
    fun invoke(): Result<BotOutput, BotError> {
        val output = BotOutput(
            plainText = "FireFrog invitation: $URL_INVITATION"
        )

        return Result.Success(output)
    }
}
