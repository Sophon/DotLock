package io.github.sophon.deadlock.remote

import io.github.sophon.core.arch.DataError
import io.github.sophon.core.arch.Result
import io.github.sophon.core.network.safeCall
import io.github.sophon.deadlock.URL_ABILITY
import io.github.sophon.deadlock.URL_HERO
import io.github.sophon.deadlock.URL_ITEM
import io.github.sophon.deadlock.remote.dto.AbilityDto
import io.github.sophon.deadlock.remote.dto.HeroDto
import io.github.sophon.deadlock.remote.dto.ItemDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.decodeFromJsonElement

internal interface DeadlockWikiDataSource {
    suspend fun downloadHeroList(): Result<Map<String, HeroDto>, DataError.Remote>
    suspend fun downloadAbilityList(): Result<Map<String, AbilityDto>, DataError.Remote>
    suspend fun downloadItemList(): Result<Map<String, ItemDto>, DataError.Remote>
}

internal class DeadlockWikiDataSourceImpl(
    private val httpClient: HttpClient,
    private val json: Json,
): DeadlockWikiDataSource {
    /**
     * Deadlock wiki data has type inconsistencies (e.g. fields are sometimes
     * a primitive instead of an object), so we deserialize entries individually
     * and skip any that fail to parse.
     */

    override suspend fun downloadHeroList(): Result<Map<String, HeroDto>, DataError.Remote> {
//        return safeCall<Map<String, HeroDto>> { httpClient.get(URL_HERO) }
        return safeCall {
            val raw = httpClient.get(URL_HERO).body<JsonObject>()
            raw.entries.mapNotNull { (key, value) ->
                try { key to json.decodeFromJsonElement<HeroDto>(value) }
                catch (_: Exception) { null }
            }.toMap()
        }
    }

    override suspend fun downloadAbilityList(): Result<Map<String, AbilityDto>, DataError.Remote> {
//        return safeCall<Map<String, AbilityDto>> { httpClient.get(URL_ABILITY) }
        return safeCall {
            val raw = httpClient.get(URL_ABILITY).body<JsonObject>()
            raw.entries.mapNotNull { (key, value) ->
                try { key to json.decodeFromJsonElement<AbilityDto>(value) }
                catch (_: Exception) { null }
            }.toMap()
        }
    }

    override suspend fun downloadItemList(): Result<Map<String, ItemDto>, DataError.Remote> {
//        return safeCall<Map<String, ItemDto>> { httpClient.get(URL_ITEM) }
        return safeCall {
            val raw = httpClient.get(URL_ITEM).body<JsonObject>()
            raw.entries.mapNotNull { (key, value) ->
                try { key to json.decodeFromJsonElement<ItemDto>(value) }
                catch (_: Exception) { null }
            }.toMap()
        }
    }
}
