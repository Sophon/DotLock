package io.github.sophon.deadlock.db

import io.github.aakira.napier.Napier
import io.github.sophon.core.arch.EmptyResult
import io.github.sophon.core.domain.model.Item
import io.github.sophon.core.arch.Result
import io.github.sophon.core.arch.WikiError

internal interface ItemDatabase {
    suspend fun insert(itemList: List<Item>): EmptyResult<WikiError>
    suspend fun fetchItem(name: String): Result<Item, WikiError>
    suspend fun fetchItemList(
        predicate: (Item) -> Boolean = { true },
    ): Result<List<Item>, WikiError>
    suspend fun clear(): EmptyResult<WikiError>
}

internal class ItemDatabaseImpl : ItemDatabase {
    private val itemMap = mutableMapOf<String, Item>()
    private val aliasMap = mutableMapOf<String, String>()

    override suspend fun insert(itemList: List<Item>): EmptyResult<WikiError> {
        itemList.forEach { item ->
            val key = if (itemMap.containsKey(item.key)) {
                Napier.w(tag = TAG) { "Item already exists: ${item.name}" }
                item.altKey
            } else {
                item.key
            }
            itemMap[key] = item
        }
        return Result.Success(Unit)
    }

    override suspend fun fetchItem(name: String): Result<Item, WikiError> {
        val key = name.lowercase()
        val item = itemMap[key] ?: aliasMap[key]?.let { itemMap[it] }
        return item?.let { Result.Success(it) }
            ?: Result.Error(WikiError.NotFound("Item not found: $name"))
    }

    override suspend fun fetchItemList(predicate: (Item) -> Boolean): Result<List<Item>, WikiError> {
        return Result.Success(itemMap.values.filter(predicate))
    }

    override suspend fun clear(): EmptyResult<WikiError> {
        itemMap.clear()
        aliasMap.clear()
        return Result.Success(Unit)
    }


    private companion object {
        const val TAG = "ItemDatabase"
    }
}
