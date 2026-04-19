package io.github.sophon.deadlock.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
internal data class HeroAbilitiesDto(
    @SerialName("Name") val heroName: String? = null,
    @SerialName("1") val ability1: AbilityDto? = null,
    @SerialName("2") val ability2: AbilityDto? = null,
    @SerialName("3") val ability3: AbilityDto? = null,
    @SerialName("4") val ability4: AbilityDto? = null,
) {
    fun abilities(): List<AbilityDto> {
        return buildList {
            ability1?.let { add(it) }
            ability2?.let { add(it) }
            ability3?.let { add(it) }
            ability4?.let { add(it) }
        }
    }
}

@Serializable
internal data class AbilityDto(
    @SerialName("Name") val name: String? = null,
    @SerialName("Key") val key: String? = null,
    @SerialName("DescKey") val descKey: String? = null,
    @SerialName("Info1") val info1: InfoDto? = null,
    @SerialName("Info2") val info2: InfoDto? = null,
    @SerialName("Info3") val info3: InfoDto? = null,
    @SerialName("Upgrades") val upgrades: List<Map<String, JsonElement>> = emptyList(),
    @SerialName("Move") val move: Map<String, PropDto>? = null,
    @SerialName("Other") val other: Map<String, PropDto>? = null,
    @SerialName("Range") val range: Map<String, PropDto>? = null,
    @SerialName("Duration") val duration: Map<String, PropDto>? = null,
    @SerialName("Cooldown") val cooldown: Map<String, PropDto>? = null,
    @SerialName("AbilityCastDelay") val abilityCastDelay: PropDto? = null,
    @SerialName("AbilityCastRange") val abilityCastRange: PropDto? = null,
    @SerialName("AbilityCharges") val abilityCharges: PropDto? = null,
    @SerialName("AbilityChannelTime") val abilityChannelTime: PropDto? = null,
    @SerialName("AbilityCooldown") val abilityCooldown: PropDto? = null,
    @SerialName("AbilityCooldownBetweenCharge") val abilityCooldownBetweenCharge: PropDto? = null,
    @SerialName("AbilityDuration") val abilityDuration: PropDto? = null,
    @SerialName("Radius") val radius: PropDto? = null,
    @SerialName("Debuff") val debuff: Map<String, PropDto>? = null,
    @SerialName("Health") val health: Map<String, PropDto>? = null,
    @SerialName("Damage") val damage: Map<String, PropDto>? = null,
)

@Serializable
internal data class InfoDto(
    @SerialName("Main") val main: MainDto? = null,
    @SerialName("Alt") val alt: List<PropDto> = emptyList(),
    @SerialName("DescKey") val descKey: String? = null,
)

@Serializable
internal data class MainDto(
    @SerialName("Props") val props: List<PropDto> = emptyList(),
)

@Serializable
internal data class PropDto(
    @SerialName("Key") val key: String? = null,
    @SerialName("Name") val name: String? = null,
    @SerialName("Title") val title: String? = null,
    @SerialName("Value") val value: JsonElement? = null,
    @SerialName("Scale") val scale: ScaleDto? = null,
    @SerialName("Type") val type: String? = null,
    @SerialName("StatusEffect") val statusEffect: String? = null,
)
