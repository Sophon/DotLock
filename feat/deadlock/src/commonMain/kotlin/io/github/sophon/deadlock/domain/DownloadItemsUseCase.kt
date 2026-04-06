package io.github.sophon.deadlock.domain

import io.github.sophon.core.arch.Result
import io.github.sophon.core.arch.WikiError
import io.github.sophon.core.arch.map
import io.github.sophon.core.arch.mapError
import io.github.sophon.core.domain.model.Item
import io.github.sophon.deadlock.data.DeadlockWikiDataSource
import io.github.sophon.deadlock.data.mapper.toDomain

internal class DownloadItemsUseCase(
    private val source: DeadlockWikiDataSource,
) {
    suspend fun invoke(): Result<List<Item>, WikiError> {
        return source.downloadItemList()
            .map { map -> map.entries.map { it.toDomain() } }
            .mapError { it.toDomain() }
    }
}
