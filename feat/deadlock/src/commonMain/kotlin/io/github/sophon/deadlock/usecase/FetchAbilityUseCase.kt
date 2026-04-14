package io.github.sophon.deadlock.usecase

import io.github.sophon.core.arch.Result
import io.github.sophon.core.arch.WikiError
import io.github.sophon.core.domain.model.Ability
import io.github.sophon.deadlock.db.AbilityDatabase

internal class FetchAbilityUseCase(
    private val db: AbilityDatabase,
) {
    suspend fun invoke(abilityName: String): Result<Ability, WikiError> {
        return db.fetchAbility(abilityName)
    }
}