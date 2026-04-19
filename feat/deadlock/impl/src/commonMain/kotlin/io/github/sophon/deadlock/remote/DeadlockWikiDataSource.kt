package io.github.sophon.deadlock.remote

import io.github.aakira.napier.Napier
import io.github.sophon.core.arch.DataError
import io.github.sophon.core.arch.Result
import io.github.sophon.core.arch.map
import io.github.sophon.core.network.safeCall
import io.github.sophon.deadlock.BASE_URL
import io.github.sophon.deadlock.URL_ABILITY
import io.github.sophon.deadlock.URL_DESCRIPTION
import io.github.sophon.deadlock.URL_HERO
import io.github.sophon.deadlock.URL_ITEM
import io.github.sophon.deadlock.remote.dto.HeroAbilitiesDto
import io.github.sophon.deadlock.remote.dto.HeroDto
import io.github.sophon.deadlock.remote.dto.ImageUrlResponseDto
import io.github.sophon.deadlock.remote.dto.ItemDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.decodeFromJsonElement

internal interface DeadlockWikiDataSource {
    suspend fun downloadDescriptions(): Result<Map<String, String>, DataError.Remote>
    suspend fun downloadHeroList(): Result<Map<String, HeroDto>, DataError.Remote>
    suspend fun downloadAbilityList(): Result<Map<String, HeroAbilitiesDto>, DataError.Remote>
    suspend fun downloadItemList(): Result<Map<String, ItemDto>, DataError.Remote>
    suspend fun getImageUrl(fileNames: List<String>): Result<Map<String, String>, DataError.Remote>
}

internal class DeadlockWikiDataSourceImpl(
    private val httpClient: HttpClient,
    private val json: Json,
): DeadlockWikiDataSource {
    override suspend fun downloadDescriptions(): Result<Map<String, String>, DataError.Remote> {
        return safeDownload(URL_DESCRIPTION, "descriptions")
    }

    /**
     * Deadlock wiki data has type inconsistencies (e.g. fields are sometimes
     * a primitive instead of an object), so we deserialize entries individually
     * and skip any that fail to parse.
     */
    override suspend fun downloadHeroList() = safeDownload<HeroDto>(URL_HERO, "heroes")

    override suspend fun downloadAbilityList() = safeDownload<HeroAbilitiesDto>(URL_ABILITY, "abilities")

    override suspend fun downloadItemList() = safeDownload<ItemDto>(URL_ITEM, "items")

    override suspend fun getImageUrl(fileNames: List<String>): Result<Map<String, String>, DataError.Remote> {
        return getWikiImageUrl(
            httpClient = httpClient,
            fileNames = fileNames,
            url = BASE_URL,
        )
    }

    private suspend inline fun <reified T> safeDownload(
        url: String,
        label: String,
    ): Result<Map<String, T>, DataError.Remote> {
        var skippedCount = 0

        val result = safeCall {
            val raw = httpClient.get(url).body<JsonObject>()
            raw.entries.mapNotNull { (key, value) ->
                try {
                    key to json.decodeFromJsonElement<T>(value)
                } catch (e: SerializationException) {
                    Napier.e(tag = TAG) { e.toString() }
                    skippedCount++
                    null
                } catch (e: Exception) {
                    Napier.e(tag = TAG) { e.toString() }
                    null
                }
            }.toMap()
        }

        if (skippedCount != 0) {
            Napier.d(tag = TAG) { "Skipped $label: $skippedCount" }
        }

        return result
    }
    
    private suspend fun getWikiImageUrl(
        httpClient: HttpClient,
        url: String,
        fileNames: List<String>,
    ): Result<Map<String, String>, DataError.Remote> {
        if (fileNames.isEmpty()) return Result.Success(emptyMap())

        val allResults = mutableMapOf<String, String>()

        // Process in chunks of 50
        fileNames.chunked(50).forEach { chunk ->
            val result = safeCall {
                httpClient.get(url) {
                    parameter("action", "query")
                    parameter("titles", chunk.joinToString("|") { "File:$it" })
                    parameter("prop", "imageinfo")
                    parameter("iiprop", "url")
                    parameter("format", "json")
                }.body<ImageUrlResponseDto>()
            }.map { response ->
                if (response.query == null) {
                    Napier.e(tag = TAG) {
                        "API error: ${response.error?.info} ($url)"
                    }
                    return@map emptyMap()
                }

                // Build map from normalized titles back to original filenames
                val normalizedMap = response.query.normalized?.associate {
                    it.to to it.from.removePrefix("File:")
                } ?: emptyMap()

                response.query.pages.values.mapNotNull { page ->
                    if (page.missing != null) return@mapNotNull null
                    val url = page.imageinfo?.firstOrNull()?.url ?: return@mapNotNull null
                    val originalFileName = normalizedMap[page.title] ?: page.title.removePrefix("File:")
                    originalFileName to url
                }.toMap()
            }

            when (result) {
                is Result.Success -> allResults.putAll(result.data)
                is Result.Error -> return result
            }
        }

        return Result.Success(allResults)
    }


    private companion object {
        const val TAG = "DeadlockWikiDataSource"
    }
}
