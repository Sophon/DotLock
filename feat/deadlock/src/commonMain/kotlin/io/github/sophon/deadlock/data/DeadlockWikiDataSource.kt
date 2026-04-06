package io.github.sophon.deadlock.data

import io.github.sophon.core.arch.DataError
import io.github.sophon.core.arch.Result
import io.github.sophon.core.network.safeCall
import io.github.sophon.deadlock.URL_ABILITY
import io.github.sophon.deadlock.URL_HERO
import io.github.sophon.deadlock.URL_ITEM
import io.github.sophon.deadlock.data.dto.AbilityDto
import io.github.sophon.deadlock.data.dto.HeroDto
import io.github.sophon.deadlock.data.dto.ItemDto
import io.ktor.client.HttpClient
import io.ktor.client.request.get

internal interface DeadlockWikiDataSource {
    suspend fun downloadHeroList(): Result<Map<String, HeroDto>, DataError.Remote>
    suspend fun downloadAbilityList(): Result<Map<String, AbilityDto>, DataError.Remote>
    suspend fun downloadItemList(): Result<Map<String, ItemDto>, DataError.Remote>
}

internal class DeadlockWikiDataSourceImpl(
    private val httpClient: HttpClient,
): DeadlockWikiDataSource {
    override suspend fun downloadHeroList(): Result<Map<String, HeroDto>, DataError.Remote> {
        val result = safeCall<Map<String, HeroDto>> { httpClient.get(URL_HERO) }
        return result
    }

    override suspend fun downloadAbilityList(): Result<Map<String, AbilityDto>, DataError.Remote> {
        val result = safeCall<Map<String, AbilityDto>> { httpClient.get(URL_ABILITY) }
        return result
    }

    override suspend fun downloadItemList(): Result<Map<String, ItemDto>, DataError.Remote> {
        val result = safeCall<Map<String, ItemDto>> { httpClient.get(URL_ITEM) }
        return result
    }
}
