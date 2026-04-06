package io.github.sophon.deadlock.domain

import io.github.sophon.core.arch.Result
import io.github.sophon.core.arch.WikiError
import io.github.sophon.core.arch.map
import io.github.sophon.core.arch.mapError
import io.github.sophon.core.domain.model.Ability
import io.github.sophon.deadlock.remote.DeadlockWikiDataSource
import io.github.sophon.deadlock.remote.mapper.toDomain

internal class SyncAbilitiesUseCase(
    private val source: DeadlockWikiDataSource,
) {
    suspend fun invoke(): Result<List<Ability>, WikiError> {
        return source.downloadAbilityList()
            .map { map -> map.entries.map { it.toDomain() } }
            .mapError { it.toDomain() }
    }
}
