package io.github.sophon.discord.domain

data class Source(
    val username: String,
    val id: String,
    val channelId: String,
    val serverName: String = "",
)
