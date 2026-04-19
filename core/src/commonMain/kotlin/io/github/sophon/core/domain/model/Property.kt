package io.github.sophon.core.domain.model

data class Property(
    val key: String,
    val type: String?,
    val value: ScaledValue,
)
