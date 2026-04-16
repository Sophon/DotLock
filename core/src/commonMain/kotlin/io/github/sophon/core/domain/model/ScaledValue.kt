package io.github.sophon.core.domain.model

import io.github.sophon.core.domain.model.ScaledValue.ScaleType

data class ScaledValue(
    val value: Double,
    val scale: Scale? = null
) {
    enum class ScaleType {
        SPIRIT,
        WEAPON_DAMAGE,
        WEAPON_DAMAGE_INCREASE,
        WEAPON_POWER,
        DURATION,
        POWER_INCREASE,
        RANGE,
        MELEE,
        HEAVY_MELEE,
        HEALING,
        COOLDOWN,
        DAMAGE,
        STATS_COUNT,
        PARRY_CD,
    }

    override fun toString(): String {
        return if (scale == null || scale.value == 0.0) {
            value.format()
        } else {
            "${value.format()}×${scale.value.format()} (${scale.type})"
        }
    }

    private fun Double.format(): String {
        return "%.2f"
            .format(this)
            .trimEnd('0')
            .trimEnd('.')
    }
}

data class Scale(
    val value: Double,
    val type: ScaleType
)
