package io.github.sophon.deadlock.domain

import io.github.sophon.core.arch.EmptyResult
import io.github.sophon.core.arch.Result
import io.github.sophon.core.arch.WikiError
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope

internal class SyncDataUseCase(
    private val syncAbilitiesUseCase: SyncAbilitiesUseCase,
    private val syncHeroesUseCase: SyncHeroesUseCase,
    private val syncItemsUseCase: SyncItemsUseCase,
) {
    suspend fun invoke(): EmptyResult<WikiError> {
        return coroutineScope {
            val results = listOf(
                async { syncAbilitiesUseCase.invoke() },
                async { syncHeroesUseCase.invoke() },
                async { syncItemsUseCase.invoke() },
            ).awaitAll()

            for (result in results) {
                if (result is Result.Error) return@coroutineScope result
            }

            Result.Success(Unit)
        }
    }
}
