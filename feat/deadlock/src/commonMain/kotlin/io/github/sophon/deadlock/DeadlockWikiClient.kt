package io.github.sophon.deadlock

import io.github.sophon.core.arch.EmptyResult
import io.github.sophon.core.arch.Error
import io.github.sophon.core.arch.Result
import io.github.sophon.core.arch.WikiError
import io.github.sophon.core.domain.model.Ability
import io.github.sophon.core.domain.model.Hero
import io.github.sophon.core.domain.model.Item
import io.github.sophon.deadlock.domain.SyncDataUseCase

interface DeadlockWikiClient {
    suspend fun downloadAllData(): EmptyResult<Error>

    suspend fun fetchHero(heroName: String): Result<Hero, WikiError>
    suspend fun fetchAbility(abilityName: String): Result<Ability, WikiError>
    suspend fun fetchAbilities(heroName: String): Result<List<Ability>, WikiError>
    suspend fun fetchItem(itemName: String): Result<Item, WikiError>
}


internal class DeadlockWikiClientImpl(
    private val syncDataUseCase: SyncDataUseCase,
): DeadlockWikiClient {
    override suspend fun downloadAllData(): EmptyResult<WikiError> {
        return syncDataUseCase.invoke()
    }

    override suspend fun fetchHero(heroName: String): Result<Hero, WikiError> {
        TODO("Not yet implemented")
    }

    override suspend fun fetchAbility(abilityName: String): Result<Ability, WikiError> {
        TODO("Not yet implemented")
    }

    override suspend fun fetchAbilities(heroName: String): Result<List<Ability>, WikiError> {
        TODO("Not yet implemented")
    }

    override suspend fun fetchItem(itemName: String): Result<Item, WikiError> {
        TODO("Not yet implemented")
    }


    private companion object {
        const val TAG = "DeadlockWikiClient"
    }
}
