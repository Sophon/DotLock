package io.github.sophon.deadlock.remote.mapper

import io.github.aakira.napier.Napier
import io.github.sophon.core.domain.model.Ability
import io.github.sophon.core.domain.model.Effect
import io.github.sophon.core.domain.model.Scale
import io.github.sophon.core.domain.model.ScaledValue
import io.github.sophon.core.util.cleanDescription
import io.github.sophon.deadlock.remote.dto.AbilityDto
import io.github.sophon.deadlock.remote.dto.InfoDto
import io.github.sophon.deadlock.remote.dto.PropDto
import io.github.sophon.deadlock.remote.dto.ScaleDto
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.doubleOrNull

internal fun AbilityDto.toDomain(
    heroName: String,
    imageUrlMap: Map<String, String>,
    descriptionMap: Map<String, String>,
): Ability {
    val upgradeList = upgrades.map { it.toUpgradeMap() }
    val propertyMap = collectProperties().toPropertyMap()
    val abilityName = if (name == null) {
        Napier.e(tag = TAG) { "null name in $heroName" }
        ""
    } else name
    val abilityKey = abilityName
        .lowercase()
        .replace(" ", "_")

    val ability = Ability(
        key = abilityKey,
        heroName = heroName,
        name = abilityName,
        description = descriptionMap[descKey]?.cleanDescription(),
        imageUrl = imageUrlMap[abilityKey],
        upgradeList = upgradeList,
        castMap = propertyMap,
        effectSet = listOf(
            info1, info2, info3,
        ).toDomainBonusList(),
    )

    return ability
}

private fun AbilityDto.collectProperties(): Map<String, PropDto> {
    return buildMap {
        abilityCastDelay?.let { put("AbilityCastDelay", it) }
        abilityCastRange?.let { put("AbilityCastRange", it) }
        abilityCharges?.let { put("AbilityCharges", it) }
        abilityChannelTime?.let { put("AbilityChannelTime", it) }
        abilityCooldown?.let { put("AbilityCooldown", it) }
        abilityCooldownBetweenCharge?.let { put("AbilityCooldownBetweenCharge", it) }
        abilityDuration?.let { put("AbilityDuration", it) }
        radius?.let { put("Radius", it) }
    }
}

private fun Map<String, JsonElement>.toUpgradeMap(): Map<String, Double> {
    return buildMap {
        for ((k, element) in this@toUpgradeMap) {
            if (k == "DescKey") continue
            val numeric = element.toNumericValue() ?: continue
            put(k, numeric)
        }
    }
}

private fun Map<String, PropDto>.toPropertyMap(): Map<String, Effect> {
    return buildMap {
        for ((pascalKey, prop) in this@toPropertyMap) {
            val scaledValue = prop.toScaledValue() ?: continue
            val displayName = prop.name ?: pascalKey
            val effect = Effect(
                key = pascalKey,
                value = scaledValue,
                type = prop.type,
            )
            put(displayName, effect)
        }
    }
}

private fun PropDto.toScaledValue(): ScaledValue? {
    val baseValue = value?.toNumericValue() ?: return null
    val mappedScale = scale?.toDomainScale()
    return ScaledValue(value = baseValue, scale = mappedScale)
}

private fun ScaleDto.toDomainScale(): Scale? {
    val mappedType = type?.toScaleType() ?: return null
    return Scale(value = value ?: 0.0, type = mappedType)
}

private fun String.toScaleType(): ScaledValue.ScaleType? {
    val upper = uppercase()
    return ScaledValue.ScaleType.entries.find { it.name == upper }
}

private fun JsonElement.toNumericValue(): Double? {
    return when (this) {
        is JsonPrimitive -> if (isString) null else doubleOrNull
        is JsonObject -> (get("Value") as? JsonPrimitive)?.doubleOrNull
        else -> null
    }
}

private fun List<InfoDto?>.toDomainBonusList(): Set<Effect> {
    return filterNotNull()
        .flatMap { it.main?.props.orEmpty() + it.alt }
        .mapNotNull { prop ->
            if (prop.key == null) return@mapNotNull null
            prop.toProperty()
        }
        .toSet()
}

private fun PropDto.toProperty(): Effect? {
    val scaledValue = toScaledValue() ?: return null

    return Effect(
        key = name.orEmpty().replace(" ", "_"),
        value = scaledValue,
        type = type,
    )
}


private const val TAG = "AbilityMapper"
