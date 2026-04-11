package io.github.sophon.deadlock.usecase

import io.github.aakira.napier.Napier
import io.github.sophon.core.arch.EmptyResult
import io.github.sophon.core.arch.WikiError
import io.github.sophon.core.arch.flatMap
import io.github.sophon.core.arch.mapError
import io.github.sophon.deadlock.db.AbilityDatabase
import io.github.sophon.deadlock.remote.DeadlockWikiDataSource
import io.github.sophon.deadlock.remote.mapper.toDomain

internal class SyncAbilitiesUseCase(
    private val source: DeadlockWikiDataSource,
    private val db: AbilityDatabase,
) {
    suspend fun invoke(): EmptyResult<WikiError> {
        return source.downloadAbilityList()
            .mapError { it.toDomain() }
            .flatMap { map ->
                val abilityList = map.entries
                    .map { it.toDomain() }
                    .filter { it.key.isNotBlank() }
                Napier.d(tag = TAG) { "${abilityList.size} abilities downloaded" }
                db.insert(abilityList)
            }
    }

    private companion object {
        const val TAG = "SyncAbilitiesUseCase"
    }
}
