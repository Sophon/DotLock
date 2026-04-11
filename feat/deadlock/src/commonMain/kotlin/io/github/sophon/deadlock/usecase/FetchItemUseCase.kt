package io.github.sophon.deadlock.usecase

import io.github.sophon.core.arch.Result
import io.github.sophon.core.arch.WikiError
import io.github.sophon.core.domain.model.Item
import io.github.sophon.deadlock.db.ItemDatabase

internal class FetchItemUseCase(
    private val db: ItemDatabase,
) {
    suspend fun invoke(itemName: String): Result<Item, WikiError> {
        return db.fetchItem(itemName)
    }
}