package io.github.sophon.deadlock

import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import io.github.sophon.core.arch.onError
import io.github.sophon.core.arch.onSuccess
import io.github.sophon.core.coreModule
import kotlinx.coroutines.coroutineScope
import org.koin.core.context.startKoin
import org.koin.mp.KoinPlatform

suspend fun main() = coroutineScope {
    startKoin {
        modules(
            coreModule(),
            deadlockModule(),
        )
    }

    Napier.base(DebugAntilog())

    val client = KoinPlatform.getKoin().get<DeadlockWikiClient>()
    client.downloadAllData()
        .onSuccess { println("Sync complete") }
        .onError { println("Sync failed $it") }

    Unit
}
