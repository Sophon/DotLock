package io.github.sophon.deadlock.usecase

import io.github.aakira.napier.Napier
import io.github.sophon.core.arch.EmptyResult
import io.github.sophon.core.arch.WikiError
import io.github.sophon.core.arch.flatMap
import io.github.sophon.core.arch.mapError
import io.github.sophon.core.util.formKey
import io.github.sophon.deadlock.db.HeroDatabase
import io.github.sophon.deadlock.remote.DeadlockWikiDataSource
import io.github.sophon.deadlock.remote.ImageResolver
import io.github.sophon.deadlock.remote.mapper.toDomain

internal class SyncHeroesUseCase(
    private val source: DeadlockWikiDataSource,
    private val db: HeroDatabase,
    private val imageResolver: ImageResolver,
) {
    suspend fun invoke(): EmptyResult<WikiError> {
        return source.downloadHeroList()
            .mapError { it.toDomain() }
            .flatMap { map ->
                val filtered = map.entries
                    .filter { it.value.isDisabled == false }
                    .filter { (it.value.name?.formKey() ?: it.key).isNotBlank() }

                val names = filtered.mapNotNull { it.value.name }

                imageResolver.resolveImageUrl(names)
                    .mapError { it.toDomain() }
                    .flatMap { imageUrls ->
                        val heroList = filtered.map { it.toDomain(imageUrls) }
                        Napier.d(tag = TAG) { "${heroList.size} heroes downloaded" }
                        db.insert(heroList)
                    }
            }
    }

    private companion object {
        const val TAG = "SyncHeroesUseCase"
    }
}
