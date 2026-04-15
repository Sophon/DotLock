package io.github.sophon.discord.feat.emoji

import io.github.aakira.napier.Napier
import io.github.sophon.core.domain.model.Bonus

internal class Emojifier(
    private val source: DiscordEmojiSource,
) {
    private val emojiMap = mutableMapOf<String, String>()

    suspend fun load() {
        source.fetch().forEach { emoji ->
            emojiMap[emoji.name] = emoji.toFormatted()
        }
        Napier.d(tag = TAG) { "Emojis: ${emojiMap.keys.size}" }
    }

    fun emojify(bonus: Bonus): String {
        val key = emojify(bonus.type) ?: return ""
        return emojiMap[key.name.lowercase()] ?: ""
    }

    fun emojify(emoji: Emoji): String {
        return emojiMap[emoji.name.lowercase()] ?: ""
    }

    enum class Emoji {
        ABILITY_CD,
        ABILITY_POINT,
        BARRIER,
        BOON,
        BULLET_FIRE_RATE,
        BULLET_RELOAD,
        BULLET_RESIST,
        BULLET_SPEED,
        BULLET_VELOCITY,
        BULLET_WEAPON_DAMAGE,
        CHANNEL_TIME,
        CHARGE_COOLDOWN,
        CLIP,
        COOLDOWN_DELAY_DURATION,
        DAMAGE_AMPLIFY,
        DAMAGE_RESIST,
        DASH,
        DISARM,
        DISPLACEMENT,
        DPS,
        HEAL_LIFESTEAL,
        HP,
        HP_REGEN,
        IMMOBILIZE,
        MELEE,
        MELEE_DAMAGE,
        MOVE_SPEED,
        MOVEMENT_SLOW,
        RADIUS,
        RANGE,
        SILENCE,
        SOULS,
        SPIRIT_DAMAGE,
        SPIRIT_POWER,
        SPIRIT_RESIST,
        SPRINT_SPEED,
        STUN,
    }


    private fun emojify(type: Bonus.Type): Emoji? {
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

            else -> null
        }
    }


    private companion object {
        const val TAG = "Emojifier"
    }
}
