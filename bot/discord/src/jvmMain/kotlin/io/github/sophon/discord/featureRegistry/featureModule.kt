package io.github.sophon.discord.featureRegistry

import io.github.sophon.discord.config.ConfigLoader
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import io.github.sophon.core.arch.Result
import io.github.sophon.discord.config.BotConfig
import io.github.sophon.discord.domain.DiscordRegisteredFeature
import io.github.sophon.discord.usecase.FetchItemUseCase
import org.koin.dsl.bind

internal fun featureModule() = module {
    singleOf(::ConfigLoader)
    single {
        when (val result = get<ConfigLoader>().loadConfig()) {
            is Result.Success -> result.data
            is Result.Error -> throw IllegalStateException("Failed to load config: ${result.error}")
        }
    }
    single< BotConfig.AdminConfig> { get<BotConfig>().adminConfig!! }

    single {
        FeatureRegistry(
            features = getAll(),
        )
    }


    single<List<DiscordRegisteredFeature>> {
        val config = get<BotConfig>()
        val registry = get<FeatureRegistry>()
        val enabledFeatures = config.featureList
            .filter { it.isEnabled }
            .map { it.name }
        val features = registry.getFeatures(enabledFeatures)

        features.forEach { feature ->
            val featureConfig = config.featureList
                .find { it.name == feature.featureInfo.name }

            if (featureConfig != null) {
                feature.registerGames(featureConfig.supportedGameList)
            }
        }
//        val adminFeature: AdminDiscordFeature = get()

//        features + adminFeature
        features
    }

    singleOf(::DeadlockFeature).bind<DiscordRegisteredFeature>()

    singleOf(::FetchItemUseCase)
}
