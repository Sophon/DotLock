package io.github.sophon.deadlock.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class ScaledValueDto(
    @SerialName("Value") val value: Double? = null,
    @SerialName("Scale") val scaleDto: ScaleDto? = null,
)

@Serializable
data class ScaleDto(
    @SerialName("Value") val value: Double? = null,
    @SerialName("Type") val type: String? = null,
)
