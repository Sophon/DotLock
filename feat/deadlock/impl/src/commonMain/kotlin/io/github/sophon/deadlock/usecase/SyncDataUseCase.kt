package io.github.sophon.deadlock.usecase

import io.github.sophon.core.arch.EmptyResult
import io.github.sophon.core.arch.Result
import io.github.sophon.core.arch.WikiError
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope

internal class SyncDataUseCase(
    private val syncAbilitiesUseCase: SyncAbilitiesUseCase,
    private val syncHeroesUseCase: SyncHeroesUseCase,
    private val downloadItemsUseCase: DownloadItemsUseCase,
    private val downloadDescriptionsUseCase: DownloadDescriptionsUseCase,
) {
    suspend fun invoke(): EmptyResult<WikiError> {
        return coroutineScope {
            val descriptionMap = when (val result = downloadDescriptionsUseCase.invoke()) {
                is Result.Success -> result.data
                is Result.Error -> return@coroutineScope result
            }

            val heroNameSet = when (val result = syncHeroesUseCase.invoke()) {
                is Result.Success -> result.data
                is Result.Error -> return@coroutineScope result
            }

            val results = listOf(
                async { syncAbilitiesUseCase.invoke(heroNameSet =  heroNameSet, descriptionMap = descriptionMap) },
                async { downloadItemsUseCase.invoke(descriptionMap) },
            ).awaitAll()

            for (result in results) {
                if (result is Result.Error) {
                    return@coroutineScope result
                }
            }

            Result.Success(Unit)
        }
    }
}
