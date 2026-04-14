package io.github.sophon.deadlock.usecase

import io.github.aakira.napier.Napier
import io.github.sophon.core.arch.EmptyResult
import io.github.sophon.core.arch.WikiError
import io.github.sophon.core.arch.flatMap
import io.github.sophon.core.arch.mapError
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
                val itemList = map.entries
                    .filter { it.value.isDisabled == false }
                    .map { it.toDomain() }
                    .filter { it.key.isNotBlank() }
                Napier.d(tag = TAG) { "${itemList.size} items downloaded" }
                db.insert(itemList)
            }
    }

    private companion object {
        const val TAG = "SyncItemsUseCase"
    }
}
