package io.github.sophon.discord

import dev.kord.core.Kord
import io.github.sophon.core.coreModule
import io.github.sophon.deadlock.deadlockModule
import io.github.sophon.discord.data.FileManager
import io.github.sophon.discord.data.FileManagerImpl
import io.github.sophon.discord.domain.DiscordButtonBuilder
import io.github.sophon.discord.featureRegistry.featureModule
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
    config: KoinAppDeclaration? = null,
) = startKoin {
    config?.invoke(this)

    modules(
        discordModule(kord),
        coreModule(),

        featureModule(),
        deadlockModule(),
    )
}

fun discordModule(kord: Kord) = module {
    single {
        CoroutineScope(SupervisorJob() + Dispatchers.Default)
    }
    single { kord }

    singleOf(::DiscordBotImpl).bind<DiscordBot>()

    singleOf(::FileManagerImpl).bind<FileManager>()
    singleOf(::DiscordButtonBuilder)

    singleOf(::RouteCommandToFeatureUseCase)
    singleOf(::ResultToEmbedUseCase)
    singleOf(::CreateErrorEmbedBuilderUseCase)
    singleOf(::CreatePlainMessageUseCase)
    singleOf(::CreateEmbedUseCase)
    singleOf(::CreateMutableEmbedUseCase)
    singleOf(::HandleButtonInteractionUseCase)
}
