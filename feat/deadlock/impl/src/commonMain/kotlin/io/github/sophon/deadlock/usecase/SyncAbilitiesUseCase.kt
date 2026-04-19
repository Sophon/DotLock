package io.github.sophon.deadlock.usecase

import io.github.aakira.napier.Napier
import io.github.sophon.core.arch.EmptyResult
import io.github.sophon.core.arch.WikiError
import io.github.sophon.core.arch.flatMap
import io.github.sophon.core.arch.mapError
import io.github.sophon.core.util.formKey
import io.github.sophon.deadlock.db.AbilityDatabase
import io.github.sophon.deadlock.remote.DeadlockWikiDataSource
import io.github.sophon.deadlock.remote.ImageResolver
import io.github.sophon.deadlock.remote.mapper.toDomain

internal class SyncAbilitiesUseCase(
    private val source: DeadlockWikiDataSource,
    private val db: AbilityDatabase,
    private val imageResolver: ImageResolver,
) {
    suspend fun invoke(registeredAbilitySet: Set<String>): EmptyResult<WikiError> {
        return source.downloadAbilityList()
            .mapError { it.toDomain() }
            .flatMap { map ->
                val filtered = map.values
                    .asSequence()
                    .flatMap { heroAbilities ->
                        val heroName = heroAbilities.heroName ?: return@flatMap emptySequence()
                        heroAbilities.abilities().asSequence().map { heroName to it }
                    }
                    .filter { (_, dto) -> dto.key != null }
                    .filter { (_, dto) -> registeredAbilitySet.contains(dto.key) }
                    .filter { (_, dto) -> dto.name.isNullOrBlank().not() }
                    .filter { (_, dto) -> dto.name!!.formKey().isNotBlank() }
                    .toList()

                val names = filtered.mapNotNull { it.second.name }

                imageResolver.resolveImageUrl(names)
                    .mapError { it.toDomain() }
                    .flatMap { imageUrls ->
                        val abilityList = filtered.map { (heroName, dto) ->
                            dto.toDomain(heroName, imageUrls)
                        }
                        Napier.d(tag = TAG) { "${abilityList.size} abilities downloaded" }
                        db.insert(abilityList)
                    }
            }
    }

    private companion object {
        const val TAG = "SyncAbilitiesUseCase"
    }
}
