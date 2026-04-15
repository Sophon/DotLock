package io.github.sophon.discord.ui

import io.github.sophon.core.domain.model.Bonus


internal enum class Emoji(val id: String) {
    ABILITY_CD("<:ability_cd:1494092181621706894>"),
    ABILITY_POINT("<:ability_point:1494092183173857330>"),
    BARRIER("<:barrier:1494092184906108928>"),
    BOON("<:boon:1494092186197954700>"),
    BULLET_FIRE_RATE("<:bullet_fire_rate:1494092187779076096>"),
    BULLET_RELOAD("<:bullet_reload:1494092188932505610>"),
    BULLET_RESIST("<:bullet_resist:1494092190031417395>"),
    BULLET_SPEED("<:bullet_speed:1494092191335845909>"),
    BULLET_VELOCITY("<:bullet_velocity:1494092192246009916>"),
    BULLET_WEAPON_DAMAGE("<:bullet_weapon_damage:1494092193206501406>"),
    CHANNEL_TIME("<:channel_time:1494092194750140516>"),
    CHARGE_COOLDOWN("<:charge_cooldown:1494092195614036018>"),
    CLIP("<:clip:1494092196842967080>"),
    COOLDOWN_DELAY_DURATION("<:cooldown_delay_duration:1494092198365630615>"),
    DAMAGE_AMPLIFY("<:damage_amplify:1494092199191908404>"),
    DAMAGE_RESIST("<:damage_resist:1494092200735281364>"),
    DASH("<:dash:1494092201964339370>"),
    DISARM("<:disarm:1494092203197468792>"),
    DISPLACEMENT("<:displacement:1494092180204158996>"),
    DPS("<:dps:1494092204627460295>"),
    HEAL_LIFESTEAL("<:heal_lifesteal:1494092205885882418>"),
    HP("<:hp:1494092208041623552>"),
    HP_REGEN("<:hp_regen:1494092206716358690>"),
    IMMOBILIZE("<:immobilize:1494092209325084753>"),
    MELEE("<:melee:1494092212227539065>"),
    MELEE_DAMAGE("<:melee_damage:1494092211028103369>"),
    MOVE_SPEED("<:move_speed:1494092214110916658>"),
    MOVEMENT_SLOW("<:movement_slow:1494092215620861993>"),
    RADIUS("<:radius:1494092217571086387>"),
    RANGE("<:range:1494092218913390682>"),
    SILENCE("<:silence:1494092220372877422>"),
    SOULS("<:souls:1494092221593550990>"),
    SPIRIT_DAMAGE("<:spirit_damage:1494092222721818816>"),
    SPIRIT_POWER("<:spirit_power:1494092223824789726>"),
    SPIRIT_RESIST("<:spirit_resist:1494092225578008646>"),
    SPRINT_SPEED("<:sprint_speed:1494092226920317038>"),
    STUN("<:stun:1494092228342317137>");

    override fun toString(): String {
        return id
    }
}

fun Bonus.emojify(): String {
    return when (type) {
        Bonus.Type.ABILITY_CD,
        Bonus.Type.CD_REDUCTION,
            -> Emoji.ABILITY_CD

        Bonus.Type.BONUS_ABILITY_CHARGE,
            -> Emoji.ABILITY_POINT

        Bonus.Type.COMBAT_BARRIER,
        Bonus.Type.COMBAT_BARRIER_PER_STACK,
        Bonus.Type.VEX_BARRIER_COMBAT_BARRIER,
            -> Emoji.BARRIER

        Bonus.Type.BONUS_FIRE_RATE,
        Bonus.Type.FIRE_RATE_SLOW,
            -> Emoji.BULLET_FIRE_RATE

        Bonus.Type.ARMOR_REDUCTION_BULLET,
        Bonus.Type.BULLET_RESIST,
            -> Emoji.BULLET_RESIST

        Bonus.Type.BONUS_BULLET_SPEED_PCT,
            -> Emoji.BULLET_SPEED

        Bonus.Type.BONUS_DAMAGE,
        Bonus.Type.DAMAGE_BASE_ATTACK_PCT,
        Bonus.Type.WEAPON_POWER_DEBUFF,
            -> Emoji.BULLET_WEAPON_DAMAGE

        Bonus.Type.BONUS_CLIP_SIZE,
        Bonus.Type.BONUS_CLIP_SIZE_PCT,
        Bonus.Type.CLIP_SIZE_OVERRIDE,
            -> Emoji.CLIP

        Bonus.Type.BONUS_ABILITY_DUR_PCT,
        Bonus.Type.BUFF_DURATION,
        Bonus.Type.BURN_DURATION,
        Bonus.Type.DEBUFF_DURATION,
            -> Emoji.COOLDOWN_DELAY_DURATION

        Bonus.Type.DAMAGE_AMPLIFICATION_PER_STACK,
        Bonus.Type.VULNERABILITY_PER_STACK,
            -> Emoji.DAMAGE_AMPLIFY

        Bonus.Type.STATUS_RESIST_PCT,
            -> Emoji.DAMAGE_RESIST

        Bonus.Type.DISARM_DURATION,
            -> Emoji.DISARM

        Bonus.Type.PUSH_FORCE,
            -> Emoji.DISPLACEMENT

        Bonus.Type.DPS,
        Bonus.Type.DPS_INCREASE,
        Bonus.Type.DPS_MAX,
        Bonus.Type.MAX_DPS,
        Bonus.Type.NORMAL_DPS,
            -> Emoji.DPS

        Bonus.Type.ABILITY_LIFE_STEAL_HERO_PCT,
        Bonus.Type.BULLET_LIFE_STEAL_PCT,
        Bonus.Type.FLAT_HEALTH_HEALING,
        Bonus.Type.HEAL_AMOUNT,
        Bonus.Type.HEAL_AMP_RECEIVE_PENALTY_PCT,
        Bonus.Type.HEAL_AMP_REGEN_PENALTY_PCT,
        Bonus.Type.HEAL_LIFE_STEAL,
        Bonus.Type.HEAL_LIFE_STEAL_PCT,
        Bonus.Type.HEAL_LIFE_STRIKE,
        Bonus.Type.HEAL_ON_VEIL,
        Bonus.Type.HEAL_P_CAST,
        Bonus.Type.HEAL_P_HEADSHOT_PCT,
        Bonus.Type.HEAL_P_STACK,
        Bonus.Type.HEALING_PER_SECOND,
        Bonus.Type.LIFE_DRAIN_PER_SECOND,
            -> Emoji.HEAL_LIFESTEAL

        Bonus.Type.HEALTH_TO_DAMAGE,
        Bonus.Type.HP_BONUS,
            -> Emoji.HP

        Bonus.Type.BONUS_HEALTH_REGEN,
        Bonus.Type.BONUS_HP_REG,
        Bonus.Type.HP_REGEN_TOTAL,
        Bonus.Type.OOC_HP_REG,
        Bonus.Type.REGEN,
            -> Emoji.HP_REGEN

        Bonus.Type.IMMOBILIZE_DURATION,
            -> Emoji.IMMOBILIZE

        Bonus.Type.BONUS_MELEE_DAMAGE_PCT,
        Bonus.Type.DAMAGE_HEAVY_MELEE,
            -> Emoji.MELEE_DAMAGE

        Bonus.Type.BONUS_MOVE_SPEED,
        Bonus.Type.BONUS_MS,
            -> Emoji.MOVE_SPEED

        Bonus.Type.MOVE_SPEED_PENALTY_PER_STACK,
        Bonus.Type.MOVE_SPEED_SLOW_PCT,
        Bonus.Type.MOVEMENT_SLOW_PCT,
        Bonus.Type.SLOW_DURATION,
        Bonus.Type.SLOW_PCT,
        Bonus.Type.SLOW_PERCENT,
            -> Emoji.MOVEMENT_SLOW

        Bonus.Type.RADIUS,
        Bonus.Type.TECH_RADIUS_MULT,
            -> Emoji.RADIUS

        Bonus.Type.TECH_RANGE_MULT,
            -> Emoji.RANGE

        Bonus.Type.SILENCE_DURATION,
            -> Emoji.SILENCE

        Bonus.Type.DAMAGE_SPIRIT,
        Bonus.Type.OUTGOING_TECH_DAMAGE_PERCENT,
            -> Emoji.SPIRIT_DAMAGE

        Bonus.Type.TECH_POWER,
        Bonus.Type.TECH_POWER_PCT,
            -> Emoji.SPIRIT_POWER

        Bonus.Type.MAGIC_RESIST_RED,
        Bonus.Type.TECH_RESIST,
            -> Emoji.SPIRIT_RESIST

        Bonus.Type.BONUS_SPRINT,
        Bonus.Type.STAMINA,
        Bonus.Type.STAMINA_CD_RED,
            -> Emoji.SPRINT_SPEED

        Bonus.Type.PETRIFY_DURATION,
        Bonus.Type.SLEEP_DURATION,
        Bonus.Type.STUN_DURATION,
            -> Emoji.STUN

        else -> return ""
    }.toString()
}