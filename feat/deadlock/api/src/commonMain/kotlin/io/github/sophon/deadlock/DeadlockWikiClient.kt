package io.github.sophon.deadlock

import io.github.sophon.core.arch.EmptyResult
import io.github.sophon.core.arch.Error
import io.github.sophon.core.arch.Result
import io.github.sophon.core.arch.WikiError
import io.github.sophon.core.domain.model.Ability
import io.github.sophon.core.domain.model.Hero
import io.github.sophon.core.domain.model.Item

interface DeadlockWikiClient {
    suspend fun downloadAllData(): EmptyResult<Error>

    suspend fun fetchHero(heroName: String): Result<Hero, WikiError>
    suspend fun fetchAbility(abilityName: String): Result<Ability, WikiError>
    suspend fun fetchAbilities(heroName: String): Result<List<Ability>, WikiError>
    suspend fun fetchItem(itemName: String): Result<Item, WikiError>
}
