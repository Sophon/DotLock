package io.github.sophon.deadlock

import io.github.sophon.deadlock.data.DeadlockWikiDataSource
import io.github.sophon.deadlock.data.DeadlockWikiDataSourceImpl
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

fun deadlockModule() = module {
    singleOf(::DeadlockWikiClientImpl).bind<DeadlockWikiClient>()

    singleOf(::DeadlockWikiDataSourceImpl).bind<DeadlockWikiDataSource>()
}
