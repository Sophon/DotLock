package io.github.sophon.core.domain.model

data class FeatureInfo(
    val name: String,
    val url: String,
    val version: String,
    val supportedGameSet: Set<Game> = setOf(),
    val iconUrl: String? = null,
    val feedbackDiscordChannelId: String? = null,
)
