package io.github.sophon.discord

import dev.kord.core.Kord
import io.github.sophon.core.coreModule
import io.github.sophon.deadlock.deadlockModule
import io.github.sophon.discord.feat.config.DiscordConfig
import io.github.sophon.discord.feat.config.FileManager
import io.github.sophon.discord.feat.config.FileManagerImpl
import io.github.sophon.discord.ui.DiscordButtonBuilder
import io.github.sophon.discord.feat.emoji.data.DiscordEmojiSource
import io.github.sophon.discord.feat.featureModule
import io.github.sophon.discord.usecase.CreateEmbedUseCase
import io.github.sophon.discord.usecase.CreateErrorEmbedBuilderUseCase
import io.github.sophon.discord.usecase.CreateMutableEmbedUseCase
import io.github.sophon.discord.usecase.CreatePlainMessageUseCase
import io.github.sophon.discord.usecase.HandleButtonInteractionUseCase
import io.github.sophon.discord.usecase.ResultToEmbedUseCase
import io.github.sophon.discord.usecase.RouteCommandToFeatureUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.bind
import org.koin.dsl.module

fun initKoin(
    kord: Kord,
    discordConfig: DiscordConfig,
    config: KoinAppDeclaration? = null,
) = startKoin {
    config?.invoke(this)

    modules(
        discordModule(kord, discordConfig),
        coreModule(),

        featureModule(),
        deadlockModule(),
    )
}

fun discordModule(kord: Kord, discordConfig: DiscordConfig) = module {
    single {
        CoroutineScope(SupervisorJob() + Dispatchers.Default)
    }
    single { kord }
    single {
        DiscordEmojiSource(
            client = get(),
            appId = discordConfig.discordBotAppId,
            apiToken = discordConfig.discordBotApiKey,
        )
    }

    singleOf(::DiscordBotImpl).bind<DiscordBot>()

    singleOf(::DiscordButtonBuilder)

    singleOf(::RouteCommandToFeatureUseCase)
    singleOf(::ResultToEmbedUseCase)
    singleOf(::CreateErrorEmbedBuilderUseCase)
    singleOf(::CreatePlainMessageUseCase)
    singleOf(::CreateEmbedUseCase)
    singleOf(::CreateMutableEmbedUseCase)
    singleOf(::HandleButtonInteractionUseCase)
}