package io.github.sophon.deadlock

import io.github.sophon.core.arch.EmptyResult
import io.github.sophon.core.arch.Error
import io.github.sophon.core.arch.Result
import io.github.sophon.core.arch.WikiError
import io.github.sophon.core.domain.model.Ability
import io.github.sophon.core.domain.model.Hero
import io.github.sophon.core.domain.model.Item
import io.github.sophon.deadlock.usecase.FetchHeroUseCase
import io.github.sophon.deadlock.usecase.FetchItemUseCase
import io.github.sophon.deadlock.usecase.SyncDataUseCase

interface DeadlockWikiClient {
    suspend fun downloadAllData(): EmptyResult<Error>

    suspend fun fetchHero(heroName: String): Result<Hero, WikiError>
    suspend fun fetchAbility(abilityName: String): Result<Ability, WikiError>
    suspend fun fetchAbilities(heroName: String): Result<List<Ability>, WikiError>
    suspend fun fetchItem(itemName: String): Result<Item, WikiError>
}


internal class DeadlockWikiClientImpl(
    private val syncDataUseCase: SyncDataUseCase,
    private val fetchItemUseCase: FetchItemUseCase,
    private val fetchHeroUseCase: FetchHeroUseCase,
): DeadlockWikiClient {
    override suspend fun downloadAllData(): EmptyResult<WikiError> {
        return syncDataUseCase.invoke()
    }

    override suspend fun fetchHero(heroName: String): Result<Hero, WikiError> {
        return fetchHeroUseCase.invoke(heroName)
    }

    override suspend fun fetchAbility(abilityName: String): Result<Ability, WikiError> {
        TODO("Not yet implemented")
    }

    override suspend fun fetchAbilities(heroName: String): Result<List<Ability>, WikiError> {
        TODO("Not yet implemented")
    }

    override suspend fun fetchItem(itemName: String): Result<Item, WikiError> {
        return fetchItemUseCase.invoke(itemName)
    }


    private companion object {
        const val TAG = "DeadlockWikiClient"
    }
}
