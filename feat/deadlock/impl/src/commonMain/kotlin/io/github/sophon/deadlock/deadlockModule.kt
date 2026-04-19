package io.github.sophon.deadlock

import io.github.sophon.deadlock.db.AbilityDatabase
import io.github.sophon.deadlock.db.AbilityDatabaseImpl
import io.github.sophon.deadlock.db.HeroDatabase
import io.github.sophon.deadlock.db.HeroDatabaseImpl
import io.github.sophon.deadlock.db.ItemDatabase
import io.github.sophon.deadlock.db.ItemDatabaseImpl
import io.github.sophon.deadlock.usecase.SyncAbilitiesUseCase
import io.github.sophon.deadlock.usecase.SyncDataUseCase
import io.github.sophon.deadlock.usecase.SyncHeroesUseCase
import io.github.sophon.deadlock.usecase.SyncItemsUseCase
import io.github.sophon.deadlock.remote.DeadlockWikiDataSource
import io.github.sophon.deadlock.remote.DeadlockWikiDataSourceImpl
import io.github.sophon.deadlock.remote.ImageResolver
import io.github.sophon.deadlock.usecase.DownloadDescriptionsUseCase
import io.github.sophon.deadlock.usecase.FetchAbilityUseCase
import io.github.sophon.deadlock.usecase.FetchHeroUseCase
import io.github.sophon.deadlock.usecase.FetchItemUseCase
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

fun deadlockModule() = module {
    singleOf(::DeadlockWikiClientImpl).bind<DeadlockWikiClient>()

    single { DeadlockFeatureInfo }

    singleOf(::DeadlockWikiDataSourceImpl).bind<DeadlockWikiDataSource>()
    singleOf(::AbilityDatabaseImpl).bind<AbilityDatabase>()
    singleOf(::HeroDatabaseImpl).bind<HeroDatabase>()
    singleOf(::ItemDatabaseImpl).bind<ItemDatabase>()
    singleOf(::ImageResolver)

    singleOf(::SyncAbilitiesUseCase)
    singleOf(::SyncHeroesUseCase)
    singleOf(::SyncItemsUseCase)
    singleOf(::SyncDataUseCase)
    singleOf(::FetchItemUseCase)
    singleOf(::FetchHeroUseCase)
    singleOf(::FetchAbilityUseCase)
    singleOf(::DownloadDescriptionsUseCase)
}
