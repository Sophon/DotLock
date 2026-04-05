package io.github.sophon.deadlock

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

fun deadlockModule() = module {
    singleOf(::DeadlockWikiClientImpl).bind<DeadlockWikiClient>()
}
