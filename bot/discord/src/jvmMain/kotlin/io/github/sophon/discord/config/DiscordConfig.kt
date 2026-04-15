package io.github.sophon.discord.config

import kotlinx.serialization.Serializable

@Serializable
data class DiscordConfig(
    val discordBotAppId: String,
    val discordBotApiKey: String,
)
