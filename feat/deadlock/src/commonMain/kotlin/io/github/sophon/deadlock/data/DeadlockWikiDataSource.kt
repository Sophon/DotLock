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
    suspend fun downloadHeroList(): Result<List<HeroDto>, DataError>
    suspend fun downloadAbilityList(): Result<List<AbilityDto>, DataError>
    suspend fun downloadItemList(): Result<List<ItemDto>, DataError>
}

internal class DeadlockWikiDataSourceImpl(
    private val httpClient: HttpClient,
): DeadlockWikiDataSource {
    override suspend fun downloadHeroList(): Result<List<HeroDto>, DataError> {
        return safeCall { httpClient.get(URL_HERO) }
    }

    override suspend fun downloadAbilityList(): Result<List<AbilityDto>, DataError> {
        return safeCall { httpClient.get(URL_ABILITY) }
    }

    override suspend fun downloadItemList(): Result<List<ItemDto>, DataError> {
        return safeCall { httpClient.get(URL_ITEM) }
    }
}
