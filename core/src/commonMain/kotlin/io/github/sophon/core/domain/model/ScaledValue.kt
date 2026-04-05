package io.github.sophon.core.domain.model

data class ScaledValue(
    val value: Double,
    val scale: Scale? = null
) {
    data class Scale(
        val value: Double,
        val type: ScaleType
    )

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
}
