package io.github.sophon.deadlock.usecase

import io.github.sophon.core.arch.Result
import io.github.sophon.core.arch.WikiError
import io.github.sophon.core.domain.model.Hero
import io.github.sophon.deadlock.db.HeroDatabase

internal class FetchHeroUseCase(
    private val db: HeroDatabase,
) {
    suspend fun invoke(heroName: String): Result<Hero, WikiError> {
        return db.fetchHero(heroName)
    }
}
