package io.github.sophon.deadlock.usecase

import io.github.aakira.napier.Napier
import io.github.sophon.core.arch.EmptyResult
import io.github.sophon.core.arch.WikiError
import io.github.sophon.core.arch.flatMap
import io.github.sophon.core.arch.mapError
import io.github.sophon.core.domain.model.Item
import io.github.sophon.core.util.formKey
import io.github.sophon.deadlock.db.ItemDatabase
import io.github.sophon.deadlock.remote.DeadlockWikiDataSource
import io.github.sophon.deadlock.remote.ImageResolver
import io.github.sophon.deadlock.remote.mapper.toDomain

internal class DownloadItemsUseCase(
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
                        val itemList = filtered
                            .map { it.toDomain(imageUrlMap, descriptionMap) }
                            .resolveUpgradeKeys()
                        Napier.d(tag = TAG) { "${itemList.size} items downloaded" }

                        return db.insert(itemList)
                    }
            }
    }


    private fun List<Item>.resolveUpgradeKeys(): List<Item> {
        val aliasToName: Map<String, String> = buildMap {
            this@resolveUpgradeKeys.forEach { item ->
                item.aliasList.forEach { alias -> put(alias, item.name) }
            }
        }

        val result = this
            .resolveUpgradeKeys(aliasToName)
            .resolveUpgradeTo()

        return result
    }

    private fun List<Item>.resolveUpgradeKeys(aliasToName: Map<String, String>): List<Item> {
        val map = map { item ->
            item.copy(
                aliasList = item.aliasList.map { it.removePrefix("upgrade_") },
                upgradePath = Item.UpgradePath(
                    from = item.upgradePath.from.mapNotNull(aliasToName::get),
                    to = item.upgradePath.to.mapNotNull(aliasToName::get),
                ),
            )
        }

        return map
    }

    private fun List<Item>.resolveUpgradeTo(): List<Item> {
        val nameToUpgrades: Map<String, List<String>> = this
            .flatMap { item -> item.upgradePath.from.map { from -> from to item.name } }
            .groupBy({ it.first }, { it.second })

        val result = map { item ->
            item.copy(
                upgradePath = item.upgradePath.copy(
                    to = nameToUpgrades[item.name].orEmpty(),
                ),
            )
        }

        return result
    }


    private companion object {
        const val TAG = "SyncItemsUseCase"
    }
}
