package io.github.sophon.deadlock.db

import io.github.aakira.napier.Napier
import io.github.sophon.core.arch.EmptyResult
import io.github.sophon.core.arch.Result
import io.github.sophon.core.arch.WikiError
import io.github.sophon.core.domain.model.Hero

internal interface HeroDatabase {
    suspend fun insert(heroList: List<Hero>): EmptyResult<WikiError>
    suspend fun fetchHero(name: String): Result<Hero, WikiError>
    suspend fun fetchHeroList(
        predicate: (Hero) -> Boolean = { true },
    ): Result<List<Hero>, WikiError>
    suspend fun clear(): EmptyResult<WikiError>
}


internal class HeroDatabaseImpl: HeroDatabase {
    val heroMap = mutableMapOf<String, Hero>()
    val aliasMap = mutableMapOf<String, String>()

    override suspend fun insert(heroList: List<Hero>): EmptyResult<WikiError> {
        heroList.forEach { hero ->
            val key = if (heroMap.containsKey(hero.key)) {
                Napier.w(tag = TAG) { "Hero already exists: ${hero.name}" }
                hero.altKey
            } else {
                hero.key
            }
            heroMap[key] = hero
        }
        return Result.Success(Unit)
    }

    override suspend fun fetchHero(name: String): Result<Hero, WikiError> {
        val key = name.lowercase()
        val hero = heroMap[key] ?: aliasMap[key]?.let { heroMap[it] }
        return hero?.let { Result.Success(it) }
            ?: Result.Error(WikiError.NotFound(name))
    }

    override suspend fun fetchHeroList(predicate: (Hero) -> Boolean): Result<List<Hero>, WikiError> {
        return Result.Success(heroMap.values.filter(predicate))
    }

    override suspend fun clear(): EmptyResult<WikiError> {
        heroMap.clear()
        aliasMap.clear()
        return Result.Success(Unit)
    }


    private companion object {
        const val TAG = "HeroDatabase"
    }
}
