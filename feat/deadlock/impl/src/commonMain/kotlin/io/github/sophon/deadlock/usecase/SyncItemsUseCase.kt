package io.github.sophon.deadlock.usecase

import io.github.aakira.napier.Napier
import io.github.sophon.core.arch.EmptyResult
import io.github.sophon.core.arch.WikiError
import io.github.sophon.core.arch.flatMap
import io.github.sophon.core.arch.mapError
import io.github.sophon.core.util.formKey
import io.github.sophon.deadlock.db.ItemDatabase
import io.github.sophon.deadlock.remote.DeadlockWikiDataSource
import io.github.sophon.deadlock.remote.ImageResolver
import io.github.sophon.deadlock.remote.mapper.toDomain

internal class SyncItemsUseCase(
    private val source: DeadlockWikiDataSource,
    private val db: ItemDatabase,
    private val imageResolver: ImageResolver,
) {
    suspend fun invoke(descriptionMap: Map<String, String>): EmptyResult<WikiError> {
        return source.downloadItemList()
            .mapError {
                it.toDomain() 
            }
            .flatMap { map ->
                val filtered = map.entries
                    .filter { it.value.isDisabled == false }
                    .filter { it.value.slot != null }
                    .filter { it.value.name.isNullOrBlank().not() }
                    .filter { it.value.name!!.formKey().isNotBlank() }

                val names = filtered.mapNotNull { it.value.name }

                imageResolver.resolveImageUrl(names)
                    .mapError { it.toDomain() }
                    .flatMap { imageUrlMap ->
                        val itemList = filtered.map { it.toDomain(imageUrlMap, descriptionMap) }
                        Napier.d(tag = TAG) { "${itemList.size} items downloaded" }
                        db.insert(itemList)
                    }
            }
    }

    private companion object {
        const val TAG = "SyncItemsUseCase"
    }
}
