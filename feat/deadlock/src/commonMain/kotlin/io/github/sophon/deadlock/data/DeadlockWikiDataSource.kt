package io.github.sophon.deadlock.data

import io.github.sophon.core.arch.DataError
import io.github.sophon.core.arch.Result
import io.github.sophon.core.arch.WikiError
import io.github.sophon.core.arch.map
import io.github.sophon.core.arch.mapError
import io.github.sophon.core.domain.model.Hero
import io.github.sophon.core.network.safeCall
import io.github.sophon.deadlock.URL_ABILITY
import io.github.sophon.deadlock.URL_HERO
import io.github.sophon.deadlock.URL_ITEM
import io.github.sophon.deadlock.data.dto.AbilityDto
import io.github.sophon.deadlock.data.dto.HeroDto
import io.github.sophon.deadlock.data.dto.ItemDto
import io.github.sophon.deadlock.data.mapper.toDomain
import io.ktor.client.HttpClient
import io.ktor.client.request.get

internal interface DeadlockWikiDataSource {
    suspend fun downloadHeroList(): Result<List<Hero>, WikiError>
    suspend fun downloadAbilityList(): Result<List<AbilityDto>, DataError>
    suspend fun downloadItemList(): Result<List<ItemDto>, DataError>
}

internal class DeadlockWikiDataSourceImpl(
    private val httpClient: HttpClient,
): DeadlockWikiDataSource {
    override suspend fun downloadHeroList(): Result<List<Hero>, WikiError> {
        val result = safeCall<Map<String, HeroDto>> { httpClient.get(URL_HERO) }
        return result
            .map { map -> map.entries.map { it.toDomain() } }
            .mapError { error ->
                when (error) {
                    DataError.Remote.TOO_MANY_REQUESTS,
                    DataError.Remote.REQUEST_TIMEOUT,
                    DataError.Remote.NO_INTERNET,
                    DataError.Remote.SERVER_ERROR,
                        -> WikiError.NetworkError(error.name)

                    DataError.Remote.SERIALIZATION_ERROR -> WikiError.ParseError(error.name)
                    DataError.Remote.UNKNOWN -> WikiError.Unknown(error.name)
                }
            }
    }

    override suspend fun downloadAbilityList(): Result<List<AbilityDto>, DataError> {
        return safeCall { httpClient.get(URL_ABILITY) }
    }

    override suspend fun downloadItemList(): Result<List<ItemDto>, DataError> {
        return safeCall { httpClient.get(URL_ITEM) }
    }
}
