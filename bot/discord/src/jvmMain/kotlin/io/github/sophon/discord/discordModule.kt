package io.github.sophon.discord

import dev.kord.core.Kord
import io.github.sophon.core.coreModule
import io.github.sophon.deadlock.deadlockModule
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

        deadlockModule(),
    )
}

fun discordModule(kord: Kord) = module {
    single {
        CoroutineScope(SupervisorJob() + Dispatchers.Default)
    }
    single { kord }

    singleOf(::DiscordBotImpl).bind<DiscordBot>()
}
