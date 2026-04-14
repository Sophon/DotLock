package io.github.sophon.deadlock.remote.dto

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
internal data class HeroDto(
    @SerialName("Name") val name: String? = null,
    @SerialName("Type") val type: String? = null,
    @SerialName("InDevelopment") val inDevelopment: Boolean? = null,
    @SerialName("InHeroLabs") val inHeroLabs: Boolean? = null,
    @SerialName("IsDisabled") val isDisabled: Boolean? = null,
    @SerialName("IsRecommended") val isRecommended: Boolean? = null,
    @SerialName("IsSelectable") val isSelectable: Boolean? = null,

    @SerialName("MaxMoveSpeed") val maxMoveSpeed: Double? = null,
    @SerialName("MoveAcceleration") val moveAcceleration: Double? = null,
    @SerialName("SprintSpeed") val sprintSpeed: Double? = null,
    @SerialName("CrouchSpeed") val crouchSpeed: Double? = null,

    @SerialName("AirDashDistanceInMeters") val airDashDistanceInMeters: Double? = null,
    @SerialName("AirDashDuration") val airDashDuration: Double? = null,
    @SerialName("AirDashSpeed") val airDashSpeed: Double? = null,
    @SerialName("GroundDashDistanceInMeters") val groundDashDistanceInMeters: Double? = null,
    @SerialName("GroundDashDuration") val groundDashDuration: Double? = null,
    @SerialName("GroundDashSpeed") val groundDashSpeed: Double? = null,

    @SerialName("MaxHealth") val maxHealth: Int? = null,
    @SerialName("BaseHealthRegen") val baseHealthRegen: Double? = null,

    @SerialName("Stamina") val stamina: Int? = null,
    @SerialName("StaminaCooldown") val staminaCooldown: Double? = null,
    @SerialName("StaminaRegenPerSecond") val staminaRegenPerSecond: Double? = null,

    @SerialName("LightMeleeDamage") val lightMeleeDamage: Double? = null,
    @SerialName("HeavyMeleeDamage") val heavyMeleeDamage: Double? = null,

    @SerialName("CritDamageBonusPercent") val critDamageBonusPercent: Int? = null,
    @SerialName("CritDamageReceivedPercent") val critDamageReceivedPercent: Int? = null,

    @SerialName("AbilityResourceMax") val abilityResourceMax: Double? = null,
    @SerialName("AbilityResourceRegenPerSecond") val abilityResourceRegenPerSecond: Double? = null,

    @SerialName("BulletResist") val bulletResist: Double? = null,
    @SerialName("MeleeResist") val meleeResist: Double? = null,
    @SerialName("TechResist") val techResist: Double? = null,

    @SerialName("BulletLifesteal") val bulletLifesteal: Double? = null,
    @SerialName("HeroBulletLifestealEffectiveness") val heroBulletLifestealEffectiveness: Double? = null,
    @SerialName("HeroSpiritLifestealEffectiveness") val heroSpiritLifestealEffectiveness: Double? = null,

    @SerialName("BaseWeaponDamageIncrease") val baseWeaponDamageIncrease: Double? = null,
    @SerialName("WeaponPowerScale") val weaponPowerScale: Double? = null,
    @SerialName("ProcBuildUpRateScale") val procBuildUpRateScale: Double? = null,
    @SerialName("BuildUpRate") val buildUpRate: Double? = null,
    @SerialName("ReloadSpeed") val reloadSpeed: Double? = null,
    @SerialName("TechDuration") val techDuration: Double? = null,
    @SerialName("TechRange") val techRange: Double? = null,

    @SerialName("Lore") val lore: String? = null,
    @SerialName("Playstyle") val playstyle: String? = null,
    @SerialName("Role") val role: String? = null,

    @SerialName("BoundAbilities") val boundAbilities: Map<String, BoundAbility>? = null,
    @SerialName("Weapon") val weapon: Weapon? = null,
    @SerialName("LevelScaling") val levelScaling: LevelScaling? = null,
    @SerialName("SpiritScaling") val spiritScaling: SpiritScaling? = null,
) {
    @Serializable
    data class BoundAbility(
        @SerialName("Name") val name: String? = null,
        @SerialName("Key") val key: String? = null,
    )

    @Serializable
    data class Weapon(
        @SerialName("BulletSpeed") val bulletSpeed: Double? = null,
        @SerialName("BulletDamage") val bulletDamage: Double? = null,
        @SerialName("RoundsPerSecond") val roundsPerSecond: Double? = null,
        @SerialName("ClipSize") val clipSize: Int? = null,
        @SerialName("ReloadTime") val reloadTime: Double? = null,
        @SerialName("ReloadMovespeed") val reloadMovespeed: Double? = null,
        @SerialName("ReloadDelay") val reloadDelay: Double? = null,
        @SerialName("ReloadSingle") val reloadSingle: Boolean? = null,
        @SerialName("FalloffStartRange") val falloffStartRange: Double? = null,
        @SerialName("FalloffEndRange") val falloffEndRange: Double? = null,
        @SerialName("FalloffStartScale") val falloffStartScale: Double? = null,
        @SerialName("FalloffEndScale") val falloffEndScale: Double? = null,
        @SerialName("FalloffBias") val falloffBias: Double? = null,
        @SerialName("BulletGravityScale") val bulletGravityScale: Double? = null,
        @SerialName("BulletsPerShot") val bulletsPerShot: Int? = null,
        @SerialName("BulletsPerBurst") val bulletsPerBurst: Int? = null,
        @SerialName("BurstInterShotInterval") val burstInterShotInterval: Double? = null,
        @SerialName("ShootMoveSpeed") val shootMoveSpeed: Double? = null,
        @SerialName("HitOnceAcrossAllBullets") val hitOnceAcrossAllBullets: Boolean? = null,
        @SerialName("CanCrit") val canCrit: Boolean? = null,
        @SerialName("AmmoConsumedPerShot") val ammoConsumedPerShot: Int? = null,
        @SerialName("DPS") val dps: Double? = null,
        @SerialName("SustainedDPS") val sustainedDps: Double? = null,
        @SerialName("ExplosionRadius") val explosionRadius: Double? = null,
        @SerialName("ExplosionDamageScaleAtMaxRadius") val explosionDamageScaleAtMaxRadius: Double? = null,
        @SerialName("RoundsPerSecondAtMaxSpin") val roundsPerSecondAtMaxSpin: Double? = null,
        @SerialName("SpinAcceleration") val spinAcceleration: Double? = null,
        @SerialName("SpinDeceleration") val spinDeceleration: Double? = null,
        @SerialName("NameKey") val nameKey: String? = null,
        @SerialName("DescKey") val descKey: String? = null,
        @SerialName("WeaponTypes") val weaponTypes: List<String>? = null,
        @SerialName("AltFire") val altFire: AltFire? = null,
    ) {
        @Serializable
        data class AltFire(
            @SerialName("BulletSpeed") val bulletSpeed: Double? = null,
            @SerialName("BulletDamage") val bulletDamage: Double? = null,
            @SerialName("RoundsPerSecond") val roundsPerSecond: Double? = null,
            @SerialName("ClipSize") val clipSize: Int? = null,
            @SerialName("ReloadTime") val reloadTime: Double? = null,
            @SerialName("ReloadMovespeed") val reloadMovespeed: Double? = null,
            @SerialName("ReloadDelay") val reloadDelay: Double? = null,
            @SerialName("ReloadSingle") val reloadSingle: Boolean? = null,
            @SerialName("FalloffStartRange") val falloffStartRange: Double? = null,
            @SerialName("FalloffEndRange") val falloffEndRange: Double? = null,
            @SerialName("FalloffStartScale") val falloffStartScale: Double? = null,
            @SerialName("FalloffEndScale") val falloffEndScale: Double? = null,
            @SerialName("FalloffBias") val falloffBias: Double? = null,
            @SerialName("BulletGravityScale") val bulletGravityScale: Double? = null,
            @SerialName("BulletsPerShot") val bulletsPerShot: Int? = null,
            @SerialName("BulletsPerBurst") val bulletsPerBurst: Int? = null,
            @SerialName("BurstInterShotInterval") val burstInterShotInterval: Double? = null,
            @SerialName("ShootMoveSpeed") val shootMoveSpeed: Double? = null,
            @SerialName("HitOnceAcrossAllBullets") val hitOnceAcrossAllBullets: Boolean? = null,
            @SerialName("CanCrit") val canCrit: Boolean? = null,
            @SerialName("AmmoConsumedPerShot") val ammoConsumedPerShot: Int? = null,
            @SerialName("DPS") val dps: Double? = null,
            @SerialName("SustainedDPS") val sustainedDps: Double? = null,
            @SerialName("ExplosionRadius") val explosionRadius: Double? = null,
            @SerialName("ExplosionDamageScaleAtMaxRadius") val explosionDamageScaleAtMaxRadius: Double? = null,
            @SerialName("NameKey") val nameKey: String? = null,
            @SerialName("DescKey") val descKey: String? = null,
        )
    }

    @Serializable
    data class LevelScaling(
        @SerialName("MaxHealth") val maxHealth: Double? = null,
        @SerialName("BulletDamage") val bulletDamage: Double? = null,
        @SerialName("BulletDamageAltFire") val bulletDamageAltFire: Double? = null,
        @SerialName("LightMeleeDamage") val lightMeleeDamage: Double? = null,
        @SerialName("HeavyMeleeDamage") val heavyMeleeDamage: Double? = null,
        @SerialName("DPS") val dps: Double? = null,
        @SerialName("SustainedDPS") val sustainedDps: Double? = null,
        @SerialName("TechPower") val techPower: Double? = null,
        @SerialName("PowerIncreases") val powerIncreases: Double? = null,
        @SerialName("BulletResist") val bulletResist: Double? = null,
        @SerialName("TechResist") val techResist: Double? = null,
        @SerialName("BonusAttackRange") val bonusAttackRange: Double? = null,
    )

    @Serializable
    data class SpiritScaling(
        @SerialName("BulletDamage") val bulletDamage: Double? = null,
        @SerialName("DPS") val dps: Double? = null,
        @SerialName("SustainedDPS") val sustainedDps: Double? = null,
        @SerialName("ClipSize") val clipSize: Double? = null,
        @SerialName("MaxMoveSpeed") val maxMoveSpeed: Double? = null,
        @SerialName("SprintSpeed") val sprintSpeed: Double? = null,
        @SerialName("BaseHealthRegen") val baseHealthRegen: Double? = null,
        @SerialName("HeavyMeleeDamage") val heavyMeleeDamage: Double? = null,
        @SerialName("RoundsPerSecond") val roundsPerSecond: Double? = null,
        @SerialName("FireRate") val fireRate: Double? = null,
        @SerialName("TechResist") val techResist: Double? = null,
        @SerialName("BulletResist") val bulletResist: Double? = null,
    )
}