package io.github.sophon.core.domain.model

data class Effect(
    val key: String,
    val type: String?,
    val value: ScaledValue,
)
