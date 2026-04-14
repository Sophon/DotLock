package io.github.sophon.core.domain.model

data class Item(
    val key: String,
    val name: String,
    val imageUrl: String?,

    val description: String?,
    val isStreetBrawl: Boolean,

    val shop: ShopInfo,
    val timing: Timing?,
    val targeting: Targeting?,

    val bonusSet: Set<Bonus>,
) {
    data class Timing(
        val cooldown: Double?,
        val cooldownBetweenCharge: Double?,
        val castDelay: Double?,
        val postCastDuration: Double?,
        val channelTime: Double?,
        val channelMoveSpeed: Double?,
        val duration: Double?,
        val charges: Int?,
    )

    data class Targeting(
        val castRange: Double?,
        val unitTargetLimit: Int?,
    )

    /**
     * TODO: just have categories and then inside have String and value
     * for example category HEAL; lifeStrike: String, regen: String etc
     * this means we can filter items by heal etc
     */
    data class Bonus(
        val type: Type,
        val value: ScaledValue,
    ) {
        enum class Type {
            DAMAGE,
            DPS,
            DAMAGE_SPIRIT,
            DAMAGE_IMPACT,
            DAMAGE_BONUS_HEADSHOT,
            DAMAGE_BASE_ATTACK_PCT,
            DOT_HP_PCT,
            DAMAGE_PULSE_AMOUNT,
            DPS_INCREASE,
            DPS_MAX,
            DAMAGE_P_CHAIN,
            ARMOR_REDUCTION_BULLET,
            PROC_DAMAGE_ATTACK_DAMAGE_BASE_PCT,
            PROC_DAMAGE_ATTACK_DAMAGE_BASE_ALT_PCT,
            HP_REGEN_TOTAL,
            HP_BONUS,
            HEAL_P_STACK,
            HEAL_LIFE_STRIKE,
            HEAL_LIFE_STEAL,
            HEAL_LIFE_STEAL_PCT,
            HEAL_P_HEADSHOT_PCT,
            HEAL_P_CAST,
            REGEN,
            HEAL_ON_VEIL,
            VEX_BARRIER_COMBAT_BARRIER,
            COMBAT_BARRIER,
            BONUS_P_CHAIN,
            TECH_POWER,
            TECH_RESIST,
            BULLET_RESIST,
            BONUS_FIRE_RATE,
            BONUS_MS,
            BONUS_SPRINT,
            BONUS_HP_REG,
            OOC_HP_REG,
            STAMINA,
            STAMINA_CD_RED,
            CD_REDUCTION,
            BONUS_CLIP_SIZE,
            BONUS_CLIP_SIZE_PCT,
            BONUS_BULLET_SPEED_PCT,
            BULLET_LIFE_STEAL_PCT,
            ABILITY_LIFE_STEAL_HERO_PCT,
            SLOW_PCT,
            SLOW_DUR,
            STATUS_RESIST_PCT,
            PROC_CHANCE,
            TECH_RANGE_MULT,
            TECH_RADIUS_MULT,
            BONUS_ABILITY_DUR_PCT,
            ABILITY_CD,
            MAGIC_RESIST_RED,
            HEAL_AMP_RECEIVE_PENALTY_PCT,
            HEAL_AMP_REGEN_PENALTY_PCT,
            BONUS_ABILITY_CHARGE,
            FIRE_RATE_SLOW,
            BONUS_MELEE_DAMAGE_PCT,
            TECH_POWER_PCT,
        }
    }
}
