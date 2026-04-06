package io.github.sophon.core.domain.model

data class Ability(
    val key: String,
    val name: String?,

    val isDisabled: Boolean,

    val timing: Timing,
    val targeting: Targeting,
    val propertySet: Set<Property>,
    val upgrades: List<Upgrade>,
) {
    data class Property(
        val type: Type,
        val value: ScaledValue,
    ) {
        enum class Type {
            DAMAGE,
            DPS,
            BONUS_DAMAGE,
            HEALTH_TO_DAMAGE,
            COMBAT_BARRIER,
            COMBAT_BARRIER_PER_STACK,
            DAMAGE_SHARE_PERCENTAGE,
            HEAL_AMOUNT,
            HEALING_PER_SECOND,
            BONUS_HEALTH_REGEN,
            STOMP_DAMAGE,
            IMPACT_DAMAGE,
            EXPLOSION_DAMAGE,
            CART_DAMAGE,
            WALL_IMPACT_DAMAGE,
            LIFE_DRAIN_PER_SECOND,
            MAX_DAMAGE,
            MIN_DAMAGE,
            FLAT_HEALTH_HEALING,
            PULSE_DAMAGE,
            SWAP_DAMAGE,
            LATE_CHECKOUT_DAMAGE,
            SPIDER_DAMAGE,
            BUILDUP_PROC_DAMAGE,
            SLOW_PERCENT,
            TICK_RATE,
            RADIUS,
            DEBUFF_DURATION,
            SLOW_DURATION,
            STUN_DURATION,
            PETRIFY_DURATION,
            SLEEP_DURATION,
            SILENCE_DURATION,
            DISARM_DURATION,
            BUFF_DURATION,
            IMMOBILIZE_DURATION,
            BURN_DURATION,
            BONUS_MOVE_SPEED,
            BONUS_FIRE_RATE,
            EVASION_PERCENT,
            BULLET_RESIST,
            TECH_RESIST,
            WEAPON_POWER_DEBUFF,
            OUTGOING_TECH_DAMAGE_PERCENT,
            DAMAGE_AMPLIFICATION_PER_STACK,
            VULNERABILITY_PER_STACK,
            MOVE_SPEED_SLOW_PCT,
            MOVEMENT_SLOW_PCT,
            MOVE_SPEED_PENALTY_PER_STACK,
            CRIT_BUILDUP,
            BUILD_UP_DURATION,
            BUILD_UP_BULLET_PERCENT_PER_HIT,
            SELF_DAMAGE_PCT,
            FORWARD_VELOCITY,
            PROJECTILE_LIFETIME,
            PUSH_FORCE,
            BOUNCE_COUNT,
            BOUNCE_RADIUS,
            CLIP_SIZE_OVERRIDE,
            NUM_PROJECTILES,
            SPREAD_ANGLE_DEGREES,
            MAX_STACKS,
            MAX_LINKS,
        }
    }

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
    ) {
        sealed class UpgradeValue {
            data class Plain(val value: Double) : UpgradeValue()
            data class Scaled(val scaledValue: ScaledValue) : UpgradeValue()
        }
    }
}
