package io.github.sophon.deadlock.domain

import io.github.sophon.core.arch.EmptyResult
import io.github.sophon.core.arch.Result
import io.github.sophon.core.arch.WikiError
import io.github.sophon.core.arch.flatMap
import io.github.sophon.core.arch.map
import io.github.sophon.core.arch.mapError
import io.github.sophon.core.domain.model.Item
import io.github.sophon.deadlock.db.ItemDatabase
import io.github.sophon.deadlock.remote.DeadlockWikiDataSource
import io.github.sophon.deadlock.remote.mapper.toDomain

internal class SyncItemsUseCase(
    private val source: DeadlockWikiDataSource,
    private val db: ItemDatabase,
) {
    suspend fun invoke(): EmptyResult<WikiError> {
        return source.downloadItemList()
            .mapError { it.toDomain() }
            .flatMap { map ->
                val itemList = map.entries.map { it.toDomain() }
                db.insert(itemList)
            }
    }
}
