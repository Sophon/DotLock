package io.github.sophon.discord.usecase

import dev.kord.common.Color
import dev.kord.rest.builder.message.EmbedBuilder
import io.github.sophon.core.util.truncate
import io.github.sophon.discord.EMBED_MAX_LENGTH
import io.github.sophon.discord.domain.BotError
import io.github.sophon.discord.domain.BotOutput

internal class CreateErrorEmbedBuilderUseCase {
    fun invoke(error: BotError): Pair<BotOutput.MutableEmbedBuilder, BotOutput.ButtonSet?> {
        return createGenericError(error) to null
    }

    private fun createGenericError(error: BotError): BotOutput.MutableEmbedBuilder {
        val errorDescription = error.toString().truncate(EMBED_MAX_LENGTH)
        val primaryBuilder: EmbedBuilder.() -> Unit = {
            title = "ERROR"
            color = Color(RED)
            description = errorDescription
        }
        val leftOverBuilder: EmbedBuilder.() -> Unit = {
            title = "ERROR"
            color = Color(RED)
            description = errorDescription
        }

        return BotOutput.MutableEmbedBuilder(primaryBuilder, leftOverBuilder)
    }
}


private const val RED = 0x00FF0000