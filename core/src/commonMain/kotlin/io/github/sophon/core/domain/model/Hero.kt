package io.github.sophon.core.domain.model

data class Hero(
    val key: String,
    val name: String,
    val type: Type?,
    val loreKey: String?,
    val playstyleKey: String?,
    val roleKey: String?,

    val isInDevelopment: Boolean,
    val isInHeroLabs: Boolean,
    val isSelectable: Boolean,
    val isRecommended: Boolean,

    val maxHealth: Double,
    val baseHealthRegen: Double,
    val movement: MovementStats,
    val dash: DashStats,
    val stamina: StaminaStats,
    val melee: MeleeStats,

    val levelScaling: LevelScaling,
    val critDamageBonusPercent: Double,
    val critDamageReceivedPercent: Double,
    val procBuildUpRateScale: Double,
    val weaponPowerScale: Double,
    val bulletLifestealEffectiveness: Double,
    val spiritLifestealEffectiveness: Double,
    val techDuration: Double,
    val techRange: Double,

    val boundAbilities: List<BoundAbility>,
    val weapon: Weapon,
) {
    enum class Type {
        BRAWLER,
        MARKSMAN,
        TANK,
        SUPPORT,
        ASSASSIN,
        MYSTIC,
    }

    data class MovementStats(
        val maxMoveSpeed: Double,
        val crouchSpeed: Double,
        val sprintSpeedMultiplier: Double,
        val moveAcceleration: Double
    )

    data class DashStats(
        val airDashDistanceMeters: Double,
        val airDashDuration: Double,
        val airDashSpeed: Double,
        val groundDashDistanceMeters: Double,
        val groundDashDuration: Double,
        val groundDashSpeed: Double
    )

    data class StaminaStats(
        val stamina: Int,
        val cooldown: Double,
        val regenPerSecond: Double
    )

    data class MeleeStats(
        val lightDamage: Double,
        val heavyDamage: Double
    )

    data class LevelScaling(
        val bulletDamage: Double,
        val maxHealth: Double,
        val techPower: Double,
        val lightMeleeDamage: Double,
        val heavyMeleeDamage: Double,
        val powerIncreases: Double,
        val dps: Double? = null,
        val sustainedDps: Double? = null,
        val bonusAttackRange: Double? = null,
        val bulletResist: Double? = null
    )

    data class BoundAbility(
        val slot: Int,
        val name: String,
        val key: String
    )
}
