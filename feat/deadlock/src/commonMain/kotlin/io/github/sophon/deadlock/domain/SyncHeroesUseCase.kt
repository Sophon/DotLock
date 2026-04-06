package io.github.sophon.deadlock.domain

import io.github.aakira.napier.Napier
import io.github.sophon.core.arch.EmptyResult
import io.github.sophon.core.arch.WikiError
import io.github.sophon.core.arch.flatMap
import io.github.sophon.core.arch.mapError
import io.github.sophon.deadlock.db.HeroDatabase
import io.github.sophon.deadlock.remote.DeadlockWikiDataSource
import io.github.sophon.deadlock.remote.mapper.toDomain

internal class SyncHeroesUseCase(
    private val source: DeadlockWikiDataSource,
    private val db: HeroDatabase,
) {
    suspend fun invoke(): EmptyResult<WikiError> {
        return source.downloadHeroList()
            .mapError { it.toDomain() }
            .flatMap { map ->
                val heroList = map.entries.map { it.toDomain() }
                Napier.d(tag = TAG) { "${heroList.size} heroes downloaded" }
                db.insert(heroList)
            }
    }

    private companion object {
        const val TAG = "SyncHeroesUseCase"
    }
}
