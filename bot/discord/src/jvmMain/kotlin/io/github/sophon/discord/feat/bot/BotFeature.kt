package io.github.sophon.discord.feat.bot

import io.github.aakira.napier.Napier
import io.github.sophon.core.arch.Result
import io.github.sophon.core.domain.model.FeatureInfo
import io.github.sophon.discord.BOT_NAME
import io.github.sophon.discord.BuildKonfig
import io.github.sophon.discord.URL_IMG_FIREFROG
import io.github.sophon.discord.URL_REPO
import io.github.sophon.discord.domain.model.BotError
import io.github.sophon.discord.domain.model.BotOutput
import io.github.sophon.discord.domain.model.Command
import io.github.sophon.discord.domain.model.Source
import io.github.sophon.discord.feat.DiscordRegisteredFeature
import io.github.sophon.discord.feat.bot.usecase.CreateHelpUseCase
import io.github.sophon.discord.feat.bot.usecase.CreateInvitationUseCase
import io.github.sophon.discord.feat.bot.usecase.CreateRepoUrlUseCase

internal class BotFeature(
    private val createInvitationUseCase: CreateInvitationUseCase,
    private val createHelpUseCase: CreateHelpUseCase,
    private val createRepoUrlUseCase: CreateRepoUrlUseCase,
): DiscordRegisteredFeature {
    override val featureInfo = FeatureInfo(
        name = BOT_NAME,
        url = URL_REPO,
        iconUrl = URL_IMG_FIREFROG,
        version = BuildKonfig.VERSION,
    )
    override val supportedCommands = setOf(
        Command.Invite,
        Command.Help,
        Command.Repo,
    )

    override suspend fun start() {
        Napier.d(tag = TAG) { "FireFrog: ${featureInfo.version}" }
    }

    override suspend fun execute(
        command: Command,
        query: String?,
        origin: Source,
    ): Result<BotOutput, BotError> {
        val result = when (command) {
            Command.Invite -> createInvitationUseCase.invoke()
            Command.Help -> createHelpUseCase.invoke(featureInfo)
            Command.Repo -> createRepoUrlUseCase.invoke()
            else -> Result.Error(BotError.InvalidCommand(command.name))
        }

        return result
    }


    private companion object {
        const val TAG = "FireFrog"
    }
}
