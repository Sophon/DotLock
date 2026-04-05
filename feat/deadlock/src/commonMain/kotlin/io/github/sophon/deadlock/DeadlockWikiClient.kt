package io.github.sophon.deadlock

import io.github.sophon.core.arch.EmptyResult
import io.github.sophon.core.arch.Error
import io.github.sophon.core.arch.Result
import io.github.sophon.core.domain.model.Ability
import io.github.sophon.core.domain.model.Hero
import io.github.sophon.core.domain.model.Item

interface DeadlockWikiClient {
    suspend fun downloadAllData(): EmptyResult<Error>

    suspend fun fetchHero(heroName: String): Result<Hero, Error>
    suspend fun fetchAbility(abilityName: String): Result<Ability, Error>
    suspend fun fetchAbilities(heroName: String): Result<List<Ability>, Error>
    suspend fun fetchItem(itemName: String): Result<Item, Error>
}


internal class DeadlockWikiClientImpl(): DeadlockWikiClient {
    override suspend fun downloadAllData(): EmptyResult<Error> {
        TODO("Not yet implemented")
    }

    override suspend fun fetchHero(heroName: String): Result<Hero, Error> {
        TODO("Not yet implemented")
    }

    override suspend fun fetchAbility(abilityName: String): Result<Ability, Error> {
        TODO("Not yet implemented")
    }

    override suspend fun fetchAbilities(heroName: String): Result<List<Ability>, Error> {
        TODO("Not yet implemented")
    }

    override suspend fun fetchItem(itemName: String): Result<Item, Error> {
        TODO("Not yet implemented")
    }
}
