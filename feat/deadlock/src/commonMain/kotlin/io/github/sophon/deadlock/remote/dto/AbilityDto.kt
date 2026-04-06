package io.github.sophon.deadlock.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class AbilityDto(
    @SerialName("Key") val key: String? = null,
    @SerialName("Name") val name: String? = null,
    @SerialName("IsDisabled") val isDisabled: Boolean? = null,

    @SerialName("AbilityCooldown") val abilityCooldown: Double? = null,
    @SerialName("AbilityCooldownBetweenCharge") val abilityCooldownBetweenCharge: Double? = null,
    @SerialName("AbilityCastDelay") val abilityCastDelay: Double? = null,
    @SerialName("AbilityCastRange") val abilityCastRange: Double? = null,
    @SerialName("AbilityChannelTime") val abilityChannelTime: Double? = null,
    @SerialName("AbilityDuration") val abilityDuration: Double? = null,
    @SerialName("AbilityPostCastDuration") val abilityPostCastDuration: Double? = null,
    @SerialName("AbilityUnitTargetLimit") val abilityUnitTargetLimit: Int? = null,
    @SerialName("AbilityCharges") val abilityCharges: Int? = null,
    @SerialName("AbilityChargesConditionally") val abilityChargesConditionally: Int? = null,
    @SerialName("AbilityLifestealPercentHero") val abilityLifestealPercentHero: Double? = null,
    @SerialName("AbilitySpeedPct") val abilitySpeedPct: Double? = null,

    @SerialName("ChannelMoveSpeed") val channelMoveSpeed: Double? = null,

    @SerialName("Damage") val damage: ScaledValueDto? = null,
    @SerialName("DPS") val dps: ScaledValueDto? = null,
    @SerialName("HealAmount") val healAmount: ScaledValueDto? = null,
    @SerialName("BonusDamage") val bonusDamage: ScaledValueDto? = null,
    @SerialName("CombatBarrier") val combatBarrier: ScaledValueDto? = null,
    @SerialName("ImpactDamage") val impactDamage: ScaledValueDto? = null,
    @SerialName("ExplosionDamage") val explosionDamage: ScaledValueDto? = null,
    @SerialName("LandingDamage") val landingDamage: ScaledValueDto? = null,
    @SerialName("MaxDamage") val maxDamage: ScaledValueDto? = null,
    @SerialName("MinDamage") val minDamage: ScaledValueDto? = null,

    @SerialName("Radius") val radius: Double? = null,
    @SerialName("ExplosionRadius") val explosionRadius: Double? = null,
    @SerialName("TickRate") val tickRate: Double? = null,

    @SerialName("SlowPercent") val slowPercent: Double? = null,
    @SerialName("SlowDuration") val slowDuration: Double? = null,
    @SerialName("StunDuration") val stunDuration: Double? = null,
    @SerialName("DebuffDuration") val debuffDuration: Double? = null,
    @SerialName("BuffDuration") val buffDuration: Double? = null,
    @SerialName("ImmobilizeDuration") val immobilizeDuration: Double? = null,

    @SerialName("BonusMoveSpeed") val bonusMoveSpeed: Double? = null,
    @SerialName("BonusFireRate") val bonusFireRate: Double? = null,
    @SerialName("BulletResist") val bulletResist: Double? = null,
    @SerialName("TechResist") val techResist: Double? = null,

    @SerialName("MaxStacks") val maxStacks: Int? = null,

    @SerialName("Upgrades") val upgrades: List<Map<String, Double>>? = null,
)
