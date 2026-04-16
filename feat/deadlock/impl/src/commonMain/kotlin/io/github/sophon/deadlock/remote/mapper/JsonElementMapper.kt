package io.github.sophon.deadlock.remote.mapper

import io.github.sophon.core.domain.model.Scale
import io.github.sophon.core.domain.model.ScaledValue
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.contentOrNull
import kotlinx.serialization.json.doubleOrNull
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

/**
 * Resolves a [JsonElement] that may be either a plain number or a scaled value object
 * into a domain [ScaledValue].
 *
 * The upstream Deadlock wiki parser emits some fields as a bare number when there is
 * no scaling data, and as {"Value": ..., "Scale": {...}} when there is.
 */
internal fun JsonElement?.toScaledValue(): ScaledValue? {
    if (this == null) {
        return null
    }

    return when (this) {
        is JsonPrimitive -> {
            val value = doubleOrNull ?: return null
            ScaledValue(value = value, scale = null)
        }
        is JsonObject -> {
            val value = this["Value"]?.jsonPrimitive?.doubleOrNull ?: return null

            val scale = this["Scale"]?.jsonObject?.let { scaleObj ->
                val scaleValue = scaleObj["Value"]?.jsonPrimitive?.doubleOrNull ?: return@let null
                val scaleType = scaleObj["Type"]?.jsonPrimitive?.contentOrNull?.toScaleType() ?: return@let null

                Scale(
                    value = scaleValue,
                    type = scaleType,
                )
            }

            ScaledValue(value = value, scale = scale)
        }
        else -> null
    }
}

private fun String.toScaleType(): ScaledValue.ScaleType? {
    val result = when (this) {
        "spirit" -> ScaledValue.ScaleType.SPIRIT
        "weapon_damage" -> ScaledValue.ScaleType.WEAPON_DAMAGE
        "weapon_damage_increase" -> ScaledValue.ScaleType.WEAPON_DAMAGE_INCREASE
        "weapon_power" -> ScaledValue.ScaleType.WEAPON_POWER
        "duration" -> ScaledValue.ScaleType.DURATION
        "power_increase" -> ScaledValue.ScaleType.POWER_INCREASE
        "range" -> ScaledValue.ScaleType.RANGE
        "melee" -> ScaledValue.ScaleType.MELEE
        "heavy_melee" -> ScaledValue.ScaleType.HEAVY_MELEE
        "healing" -> ScaledValue.ScaleType.HEALING
        "cooldown" -> ScaledValue.ScaleType.COOLDOWN
        "damage" -> ScaledValue.ScaleType.DAMAGE
        "stats_count" -> ScaledValue.ScaleType.STATS_COUNT
        "parry_cd" -> ScaledValue.ScaleType.PARRY_CD
        else -> null
    }

    return result
}