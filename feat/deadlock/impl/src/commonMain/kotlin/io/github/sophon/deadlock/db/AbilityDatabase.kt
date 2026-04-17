package io.github.sophon.deadlock.db

import io.github.aakira.napier.Napier
import io.github.sophon.core.arch.EmptyResult
import io.github.sophon.core.arch.WikiError
import io.github.sophon.core.domain.model.Ability
import io.github.sophon.core.arch.Result

internal interface AbilityDatabase {
    suspend fun insert(abilityList: List<Ability>): EmptyResult<WikiError>
    suspend fun fetchAbility(name: String): Result<Ability, WikiError>
    suspend fun fetchAbilityList(
        predicate: (Ability) -> Boolean = { true },
    ): Result<List<Ability>, WikiError>
    suspend fun clear(): EmptyResult<WikiError>
}

internal class AbilityDatabaseImpl: AbilityDatabase {
    private val abilityMap = mutableMapOf<String, Ability>()
    private val aliasMap = mutableMapOf<String, String>()
    private val heroMap = mutableMapOf<String, String>()

    override suspend fun insert(abilityList: List<Ability>): EmptyResult<WikiError> {
        abilityList.forEach { ability ->
            val key = if (abilityMap.containsKey(ability.key)) {
                Napier.w(tag = TAG) { "Ability already exists: ${ability.name}" }
                ability.altKey
            } else {
                ability.key
            }
            abilityMap[key] = ability
        }
        return Result.Success(Unit)
    }

    override suspend fun fetchAbility(name: String): Result<Ability, WikiError> {
        val key = name.lowercase()
        val ability = abilityMap[key] ?: aliasMap[key]?.let { abilityMap[it] }
        return ability?.let { Result.Success(it) }
            ?: Result.Error(WikiError.NotFound("Ability not found: $name"))
    }

    override suspend fun fetchAbilityList(predicate: (Ability) -> Boolean): Result<List<Ability>, WikiError> {
        return Result.Success(abilityMap.values.filter(predicate))
    }

    override suspend fun clear(): EmptyResult<WikiError> {
        abilityMap.clear()
        aliasMap.clear()
        return Result.Success(Unit)
    }


    private companion object {
        const val TAG = "AbilityDatabase"
    }
}
