package io.github.sophon.discord.config

import io.github.aakira.napier.Napier
import io.github.sophon.core.arch.Result
import io.github.sophon.core.arch.map
import io.github.sophon.core.util.getGame
import io.github.sophon.discord.data.FileManager
import io.github.sophon.discord.domain.BotError
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

internal class ConfigLoader(
    private val json: Json,
    private val fileManager: FileManager,
) {
    fun loadConfig(): Result<BotConfig, BotError> {
        return fileManager.read(CONFIG_PATH)
            .map { configText ->
                val jsonConfig = json.decodeFromString<JsonConfig>(configText).apply {
                    Napier.d(tag = TAG) { this.toString() }
                }
                BotConfig(
                    featureList = jsonConfig.featureList.map { feature ->
                        BotConfig.Feature(
                            name = feature.name,
                            isEnabled = feature.isEnabled,
                            supportedGameList = feature.supportedGames.mapNotNull { gameId ->
                                gameId.getGame()
                            },
                        )
                    },
                    adminConfig = BotConfig.AdminConfig(
                        administratorIdList = jsonConfig.adminConfig.administratorIdList,
                        feedbackChannelIdList = jsonConfig.adminConfig.feedbackChannelIdList,
                        adminServerId = jsonConfig.adminConfig.adminServerId,
                    ),
                    statsConfig = BotConfig.StatsConfig(
                        isEnabled = jsonConfig.statsConfig.isEnabled,
                        statsChannelIdList = jsonConfig.statsConfig.statsChannelIdList,
                    )
                )
            }
    }


    private companion object {
        const val CONFIG_PATH = "res/config.json"
        const val TAG = "ConfigLoader"
    }

    @Serializable
    private data class JsonConfig(
        val featureList: List<Feature>,
        val adminConfig: AdminConfig,
        val statsConfig: StatsConfig,
    ) {
        @Serializable
        data class Feature(
            val name: String,
            val isEnabled: Boolean,
            val supportedGames: List<String>,
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
}
