package io.github.sophon.deadlock.data

import io.github.sophon.core.arch.DataError
import io.github.sophon.core.arch.Result
import io.github.sophon.core.arch.WikiError
import io.github.sophon.core.arch.map
import io.github.sophon.core.arch.mapError
import io.github.sophon.core.domain.model.Ability
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
    suspend fun downloadAbilityList(): Result<List<Ability>, WikiError>
    suspend fun downloadItemList(): Result<List<ItemDto>, DataError>
}

internal class DeadlockWikiDataSourceImpl(
    private val httpClient: HttpClient,
): DeadlockWikiDataSource {
    override suspend fun downloadHeroList(): Result<List<Hero>, WikiError> {
        val result = safeCall<Map<String, HeroDto>> { httpClient.get(URL_HERO) }
        return result
            .map { map -> map.entries.map { it.toDomain() } }
            .mapError { it.toDomain() }
    }

    override suspend fun downloadAbilityList(): Result<List<Ability>, WikiError> {
        val result = safeCall<Map<String, AbilityDto>> { httpClient.get(URL_ABILITY) }
        return result
            .map { map -> map.entries.map { it.toDomain() } }
            .mapError { it.toDomain() }
    }

    override suspend fun downloadItemList(): Result<List<ItemDto>, DataError> {
        return safeCall { httpClient.get(URL_ITEM) }
    }
}
