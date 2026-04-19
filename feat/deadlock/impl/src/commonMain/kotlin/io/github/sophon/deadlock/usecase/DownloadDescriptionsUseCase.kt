package io.github.sophon.deadlock.usecase

import io.github.sophon.core.arch.Result
import io.github.sophon.core.arch.WikiError
import io.github.sophon.core.arch.mapError
import io.github.sophon.deadlock.remote.DeadlockWikiDataSource
import io.github.sophon.deadlock.remote.mapper.toDomain

internal class DownloadDescriptionsUseCase(
    private val source: DeadlockWikiDataSource,
) {
    suspend fun invoke(): Result<Map<String, String>, WikiError> {
        return source.downloadDescriptions()
            .mapError { it.toDomain() }
    }
}
