package io.github.sophon.core.domain.model

data class Ability(
    val key: String,
    val name: String?,
    val isDisabled: Boolean,

    val timing: Timing,
    val targeting: Targeting,

    val damage: ScaledValue?,
    val dps: ScaledValue?,
    val bonusDamage: ScaledValue?,
    val healthToDamage: ScaledValue?,
    val combatBarrier: ScaledValue?,
    val combatBarrierPerStack: ScaledValue?,
    val damageSharePercentage: ScaledValue?,
    val healAmount: ScaledValue?,
    val healingPerSecond: ScaledValue?,
    val bonusHealthRegen: ScaledValue?,
    val stompDamage: ScaledValue?,
    val impactDamage: ScaledValue?,
    val explosionDamage: ScaledValue?,
    val cartDamage: ScaledValue?,
    val wallImpactDamage: ScaledValue?,
    val lifeDrainPerSecond: ScaledValue?,
    val maxDamage: ScaledValue?,
    val minDamage: ScaledValue?,
    val flatHealthHealing: ScaledValue?,
    val pulseDamage: ScaledValue?,
    val swapDamage: ScaledValue?,
    val lateCheckoutDamage: ScaledValue?,
    val doorwayDistance: ScaledValue?,
    val spiderDamage: ScaledValue?,
    val buildupProcDamage: ScaledValue?,
    val slowPercent: ScaledValue?,

    val tickRate: Double?,
    val radius: Double?,
    val debuffDuration: Double?,
    val slowDuration: Double?,
    val movementSlowPct: Double?,
    val moveSpeedSlowPct: Double?,
    val maxStacks: Int?,
    val damageAmplificationPerStack: Double?,
    val burnDuration: Double?,
    val burnDurationBase: Double?,
    val buildUpBulletPercentPerHit: Double?,
    val buildUpDuration: Double?,
    val critBuildup: Double?,
    val refillDuration: Double?,
    val refillDurationCrit: Double?,
    val clipSizeOverride: Int?,
    val bulletScaleFactor: Double?,
    val weaponPowerDebuff: Double?,
    val selfDamagePct: Double?,
    val armingDuration: Double?,
    val vulnerabilityPerStack: Double?,
    val moveSpeedPenaltyPerStack: Double?,
    val numProjectiles: Int?,
    val spreadAngleDegrees: Double?,
    val bonusMoveSpeed: Double?,
    val buffDuration: Double?,
    val bounceCount: Int?,
    val bounceRadius: Double?,
    val cooldownReductionPercentPerHit: Double?,
    val damageShareRadius: Double?,
    val maxLinks: Int?,
    val linkDuration: Double?,
    val immobilizeDuration: Double?,
    val tetherLeashRange: Double?,
    val timeBetweenAttacks: Double?,
    val targetingConeAngle: Double?,
    val forwardVelocity: Double?,
    val projectileLifetime: Double?,
    val pushForce: Double?,
    val minRange: Double?,
    val friendlyBulletDamageBonus: Double?,
    val auraEffectDuration: Double?,
    val timeScaleDuration: Double?,
    val stunDuration: Double?,
    val petrifyDuration: Double?,
    val sleepDuration: Double?,
    val silenceDuration: Double?,
    val disarmDuration: Double?,
    val outgoingTechDamagePercent: Double?,
    val bonusFireRate: Double?,
    val evasionPercent: Double?,
    val bulledResist: Double?,
    val techResist: Double?,

    val zone: ZoneStats?,
    val upgrades: List<Upgrade>,
) {
    data class Timing(
        val cooldown: ScaledValue?,
        val cooldownBetweenCharge: Double?,
        val castDelay: Double?,
        val postCastDuration: Double?,
        val channelTime: ScaledValue?,
        val channelMoveSpeed: Double?,
        val duration: ScaledValue?,
        val charges: Int?,
    )

    data class Targeting(
        val castRange: ScaledValue?,
        val unitTargetLimit: Int?,
    )

    data class ZoneStats(
        val width: Double?,
        val height: Double?,
        val depth: Double?,
        val depthVisualScale: Double?,
        val formationTime: Double?,
        val timeScale: Double?,
        val timeScaleFriendly: Double?,
        val numSegments: Int?,
        val segmentEmitTime: Double?,
        val timeBetweenSegments: Double?,
        val timeToMaxDistance: Double?,
        val impactRange: Double?,
    )

    data class Upgrade(
        val changes: Map<String, UpgradeValue>
    )

    sealed class UpgradeValue {
        data class Plain(val value: Double) : UpgradeValue()
        data class Scaled(val scaledValue: ScaledValue) : UpgradeValue()
    }
}
