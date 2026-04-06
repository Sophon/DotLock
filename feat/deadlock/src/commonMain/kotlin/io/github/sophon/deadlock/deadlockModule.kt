package io.github.sophon.deadlock

import io.github.sophon.deadlock.domain.SyncDataUseCase
import io.github.sophon.deadlock.domain.SyncHeroesUseCase
import io.github.sophon.deadlock.domain.SyncItemsUseCase
import io.github.sophon.deadlock.remote.DeadlockWikiDataSource
import io.github.sophon.deadlock.remote.DeadlockWikiDataSourceImpl
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

fun deadlockModule() = module {
    singleOf(::DeadlockWikiClientImpl).bind<DeadlockWikiClient>()

    singleOf(::DeadlockWikiDataSourceImpl).bind<DeadlockWikiDataSource>()

    singleOf(::SyncHeroesUseCase)
    singleOf(::SyncHeroesUseCase)
    singleOf(::SyncItemsUseCase)
    singleOf(::SyncDataUseCase)
}
