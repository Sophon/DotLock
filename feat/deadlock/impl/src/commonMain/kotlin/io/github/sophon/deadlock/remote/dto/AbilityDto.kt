package io.github.sophon.deadlock.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
internal data class AbilityDto(
    @SerialName("Key") val key: String? = null,
    @SerialName("Name") val name: String? = null,
    @SerialName("IsDisabled") val isDisabled: Boolean? = null,

    @SerialName("AbilityCooldown") val abilityCooldown: JsonElement? = null,
    @SerialName("AbilityCooldownBetweenCharge") val abilityCooldownBetweenCharge: JsonElement? = null,
    @SerialName("AbilityCastDelay") val abilityCastDelay: JsonElement? = null,
    @SerialName("AbilityCastRange") val abilityCastRange: JsonElement? = null,
    @SerialName("AbilityChannelTime") val abilityChannelTime: JsonElement? = null,
    @SerialName("AbilityDuration") val abilityDuration: JsonElement? = null,
    @SerialName("AbilityPostCastDuration") val abilityPostCastDuration: JsonElement? = null,
    @SerialName("AbilityUnitTargetLimit") val abilityUnitTargetLimit: Int? = null,
    @SerialName("AbilityCharges") val abilityCharges: JsonElement? = null,
    @SerialName("AbilityChargesConditionally") val abilityChargesConditionally: Int? = null,
    @SerialName("AbilityLifestealPercentHero") val abilityLifestealPercentHero: JsonElement? = null,
    @SerialName("AbilitySpeedPct") val abilitySpeedPct: JsonElement? = null,

    @SerialName("ChannelMoveSpeed") val channelMoveSpeed: JsonElement? = null,

    @SerialName("Damage") val damage: JsonElement? = null,
    @SerialName("DPS") val dps: JsonElement? = null,
    @SerialName("NormalDPS") val normalDps: JsonElement? = null,
    @SerialName("MaxDPS") val maxDps: JsonElement? = null,
    @SerialName("DamageHeavyMelee") val damageHeavyMelee: JsonElement? = null,
    @SerialName("HealAmount") val healAmount: JsonElement? = null,
    @SerialName("BonusDamage") val bonusDamage: JsonElement? = null,
    @SerialName("CombatBarrier") val combatBarrier: JsonElement? = null,
    @SerialName("ImpactDamage") val impactDamage: JsonElement? = null,
    @SerialName("ExplosionDamage") val explosionDamage: JsonElement? = null,
    @SerialName("LandingDamage") val landingDamage: JsonElement? = null,
    @SerialName("MaxDamage") val maxDamage: JsonElement? = null,
    @SerialName("MinDamage") val minDamage: JsonElement? = null,

    @SerialName("Radius") val radius: JsonElement? = null,
    @SerialName("ExplosionRadius") val explosionRadius: JsonElement? = null,
    @SerialName("TickRate") val tickRate: JsonElement? = null,

    @SerialName("SlowPercent") val slowPercent: JsonElement? = null,
    @SerialName("SlowDuration") val slowDuration: JsonElement? = null,
    @SerialName("StunDuration") val stunDuration: JsonElement? = null,
    @SerialName("DebuffDuration") val debuffDuration: JsonElement? = null,
    @SerialName("BuffDuration") val buffDuration: JsonElement? = null,
    @SerialName("ImmobilizeDuration") val immobilizeDuration: JsonElement? = null,

    @SerialName("BonusMoveSpeed") val bonusMoveSpeed: JsonElement? = null,
    @SerialName("BonusFireRate") val bonusFireRate: JsonElement? = null,
    @SerialName("BulletResist") val bulletResist: JsonElement? = null,
    @SerialName("TechResist") val techResist: JsonElement? = null,

    @SerialName("MaxStacks") val maxStacks: JsonElement? = null,

    @SerialName("Upgrades") val upgrades: List<Map<String, JsonElement>>? = null,
)
