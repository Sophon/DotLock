package io.github.sophon.deadlock

import io.github.sophon.core.arch.EmptyResult
import io.github.sophon.core.arch.Result
import io.github.sophon.core.arch.WikiError
import io.github.sophon.core.domain.model.Ability
import io.github.sophon.core.domain.model.Hero
import io.github.sophon.core.domain.model.Item
import io.github.sophon.deadlock.usecase.FetchAbilityUseCase
import io.github.sophon.deadlock.usecase.FetchHeroUseCase
import io.github.sophon.deadlock.usecase.FetchItemUseCase
import io.github.sophon.deadlock.usecase.SyncDataUseCase

internal class DeadlockWikiClientImpl(
    private val syncDataUseCase: SyncDataUseCase,
    private val fetchItemUseCase: FetchItemUseCase,
    private val fetchHeroUseCase: FetchHeroUseCase,
    private val fetchAbilityUseCase: FetchAbilityUseCase,
): DeadlockWikiClient {
    override suspend fun downloadAllData(): EmptyResult<WikiError> {
        return syncDataUseCase.invoke()
    }

    override suspend fun fetchHero(heroName: String): Result<Hero, WikiError> {
        return fetchHeroUseCase.invoke(heroName)
    }

    override suspend fun fetchAbility(abilityName: String): Result<Ability, WikiError> {
        return fetchAbilityUseCase.invoke(abilityName)
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
