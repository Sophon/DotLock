package io.github.sophon.deadlock

import io.github.sophon.deadlock.db.AbilityDatabase
import io.github.sophon.deadlock.db.AbilityDatabaseImpl
import io.github.sophon.deadlock.db.HeroDatabase
import io.github.sophon.deadlock.db.HeroDatabaseImpl
import io.github.sophon.deadlock.db.ItemDatabase
import io.github.sophon.deadlock.db.ItemDatabaseImpl
import io.github.sophon.deadlock.domain.SyncAbilitiesUseCase
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
    singleOf(::AbilityDatabaseImpl).bind<AbilityDatabase>()
    singleOf(::HeroDatabaseImpl).bind<HeroDatabase>()
    singleOf(::ItemDatabaseImpl).bind<ItemDatabase>()

    singleOf(::SyncAbilitiesUseCase)
    singleOf(::SyncHeroesUseCase)
    singleOf(::SyncItemsUseCase)
    singleOf(::SyncDataUseCase)
}
