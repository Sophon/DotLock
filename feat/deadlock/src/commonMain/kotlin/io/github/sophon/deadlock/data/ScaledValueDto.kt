package io.github.sophon.deadlock.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class ScaledValueDto(
    @SerialName("Value") val value: Double? = null,
    @SerialName("Scale") val scale: Scale? = null,
) {
    @Serializable
    data class Scale(
        @SerialName("Value") val value: Double? = null,
        @SerialName("Type") val type: String? = null,
    )
}
