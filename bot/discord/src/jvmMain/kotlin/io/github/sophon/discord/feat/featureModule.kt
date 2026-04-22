package io.github.sophon.discord.feat

import io.github.sophon.discord.feat.config.ConfigLoader
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import io.github.sophon.core.arch.Result
import io.github.sophon.discord.feat.bot.BotFeature
import io.github.sophon.discord.feat.bot.usecase.CreateHelpUseCase
import io.github.sophon.discord.feat.bot.usecase.CreateInvitationUseCase
import io.github.sophon.discord.feat.bot.usecase.CreateRepoUrlUseCase
import io.github.sophon.discord.feat.config.BotConfig
import io.github.sophon.discord.feat.config.FileManager
import io.github.sophon.discord.feat.config.FileManagerImpl
import io.github.sophon.discord.feat.deadlock.DeadlockFeature
import io.github.sophon.discord.feat.emoji.Emojifier
import io.github.sophon.discord.feat.deadlock.usecase.FetchAbilityUseCase
import io.github.sophon.discord.feat.deadlock.usecase.FetchHeroUseCase
import io.github.sophon.discord.feat.deadlock.usecase.FetchItemUseCase
import org.koin.dsl.bind

internal fun featureModule() = module {
    //region CONFIG
    singleOf(::ConfigLoader)
    single {
        when (val result = get<ConfigLoader>().loadConfig()) {
            is Result.Success -> result.data
            is Result.Error -> throw IllegalStateException("Failed to load config: ${result.error}")
        }
    }
    single< BotConfig.AdminConfig> { get<BotConfig>().adminConfig!! }
    singleOf(::FileManagerImpl).bind<FileManager>()
    //endregion

    //region BOT
    singleOf(::BotFeature).bind<DiscordRegisteredFeature>()
    singleOf(::CreateInvitationUseCase)
    singleOf(::CreateHelpUseCase)
    singleOf(::CreateRepoUrlUseCase)
    //endregion

    //region DEADLOCK
    singleOf(::DeadlockFeature).bind<DiscordRegisteredFeature>()
    singleOf(::FetchItemUseCase)
    singleOf(::FetchHeroUseCase)
    singleOf(::FetchAbilityUseCase)
    //endregion

    singleOf(::Emojifier)

    single {
        FeatureRegistry(
            features = getAll(),
            coreFeature = get<BotFeature>(),
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
}
