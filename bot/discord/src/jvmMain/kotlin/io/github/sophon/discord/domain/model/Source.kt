package io.github.sophon.discord.domain.model

data class Source(
    val username: String,
    val id: String,
    val channelId: String,
    val serverName: String = "",
)
