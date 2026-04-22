package io.github.sophon.discord.feat.bot.usecase

import dev.kord.common.Color
import dev.kord.rest.builder.message.EmbedBuilder
import io.github.sophon.core.arch.Result
import io.github.sophon.core.domain.model.FeatureInfo
import io.github.sophon.discord.domain.model.BotError
import io.github.sophon.discord.domain.model.BotOutput
import io.github.sophon.discord.domain.model.Command
import io.github.sophon.discord.ui.featureFooter
import io.github.sophon.discord.ui.mandatoryField

internal class CreateHelpUseCase {
    fun invoke(featureInfo: FeatureInfo): Result<BotOutput, BotError> {
        val botOutput = BotOutput(
            primaryEmbedBuilder = helpEmbed(featureInfo)
        )

        return Result.Success(botOutput)
    }

    private fun helpEmbed(featureInfo: FeatureInfo): EmbedBuilder.() -> Unit = {
        title = "HOW TO USE THE BOT"
        color = Color(ORANGE)
        featureFooter(featureInfo)

        mandatoryField(
            name = "**Basic syntax**",
            value = "`@bot [command] [queries]` or `/command [queries]`",
            inline = false,
        )

        val excludedCommands = emptySet<Command>()
        val commands = Command.entries.sortedBy { it.name }
        val commandString = buildString {
            for (command in commands.filterNot { it in excludedCommands }) {
                appendLine("- `${command.name}`")
            }
        }
        mandatoryField(
            name = "Supported commands",
            value = commandString
        )
    }


    private companion object {
        const val ORANGE = 0x00FF262C
    }
}
