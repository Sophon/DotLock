package io.github.sophon.deadlock.domain

import io.github.sophon.core.arch.WikiError
import io.github.sophon.core.arch.Result
import io.github.sophon.core.domain.model.Hero
import io.github.sophon.deadlock.data.DeadlockWikiDataSource

internal class DownloadHeroesUseCase(
    private val source: DeadlockWikiDataSource,
) {
    suspend fun invoke(): Result<List<Hero>, WikiError> {
        TODO()
    }
}
