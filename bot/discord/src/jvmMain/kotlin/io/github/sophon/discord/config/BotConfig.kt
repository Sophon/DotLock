package io.github.sophon.discord.config

import io.github.sophon.core.domain.model.Game
import kotlinx.serialization.Serializable

@Serializable
data class BotConfig(
    val featureList: List<Feature>,
    val adminConfig: AdminConfig? = null,
    val statsConfig: StatsConfig? = null,
) {
    @Serializable
    data class Feature(
        val name: String,
        val isEnabled: Boolean,
        val supportedGameList: List<Game>,
    )

    @Serializable
    data class AdminConfig(
        val administratorIdList: List<String>,
        val feedbackChannelIdList: List<String>,
        val adminServerId: String,
    )

    @Serializable
    data class StatsConfig(
        val isEnabled: Boolean,
        val statsChannelIdList: List<String>,
    )
}