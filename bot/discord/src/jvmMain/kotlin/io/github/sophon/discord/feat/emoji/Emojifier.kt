package io.github.sophon.discord.feat.emoji

import io.github.aakira.napier.Napier
import io.github.sophon.core.domain.model.Property
import io.github.sophon.discord.feat.emoji.data.DiscordEmojiSource
import io.github.sophon.discord.feat.emoji.data.toFormatted

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

    fun emojify(property: Property): String {
        val key = emojify(property.key) ?: return ""
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


    private fun emojify(key: Property.Key): Emoji? {
        return when (key) {
            Property.Key.ABILITY_CD,
            Property.Key.CD_REDUCTION,
                -> Emoji.ABILITY_CD

            Property.Key.BONUS_ABILITY_CHARGE,
                -> Emoji.ABILITY_POINT

            Property.Key.COMBAT_BARRIER,
            Property.Key.COMBAT_BARRIER_PER_STACK,
            Property.Key.VEX_BARRIER_COMBAT_BARRIER,
                -> Emoji.BARRIER

            Property.Key.BONUS_FIRE_RATE,
            Property.Key.FIRE_RATE_SLOW,
                -> Emoji.BULLET_FIRE_RATE

            Property.Key.ARMOR_REDUCTION_BULLET,
            Property.Key.BULLET_RESIST,
                -> Emoji.BULLET_RESIST

            Property.Key.BONUS_BULLET_SPEED_PCT,
                -> Emoji.BULLET_SPEED

            Property.Key.BONUS_DAMAGE,
            Property.Key.DAMAGE_BASE_ATTACK_PCT,
            Property.Key.WEAPON_POWER_DEBUFF,
                -> Emoji.BULLET_WEAPON_DAMAGE

            Property.Key.BONUS_CLIP_SIZE,
            Property.Key.BONUS_CLIP_SIZE_PCT,
            Property.Key.CLIP_SIZE_OVERRIDE,
                -> Emoji.CLIP

            Property.Key.BONUS_ABILITY_DUR_PCT,
            Property.Key.BUFF_DURATION,
            Property.Key.BURN_DURATION,
            Property.Key.DEBUFF_DURATION,
                -> Emoji.COOLDOWN_DELAY_DURATION

            Property.Key.DAMAGE_AMPLIFICATION_PER_STACK,
            Property.Key.VULNERABILITY_PER_STACK,
                -> Emoji.DAMAGE_AMPLIFY

            Property.Key.STATUS_RESIST_PCT,
                -> Emoji.DAMAGE_RESIST

            Property.Key.DISARM_DURATION,
                -> Emoji.DISARM

            Property.Key.PUSH_FORCE,
                -> Emoji.DISPLACEMENT

            Property.Key.DPS,
            Property.Key.DPS_INCREASE,
            Property.Key.DPS_MAX,
            Property.Key.MAX_DPS,
            Property.Key.NORMAL_DPS,
                -> Emoji.DPS

            Property.Key.ABILITY_LIFE_STEAL_HERO_PCT,
            Property.Key.BULLET_LIFE_STEAL_PCT,
            Property.Key.FLAT_HEALTH_HEALING,
            Property.Key.HEAL_AMOUNT,
            Property.Key.HEAL_AMP_RECEIVE_PENALTY_PCT,
            Property.Key.HEAL_AMP_REGEN_PENALTY_PCT,
            Property.Key.HEAL_LIFE_STEAL,
            Property.Key.HEAL_LIFE_STEAL_PCT,
            Property.Key.HEAL_LIFE_STRIKE,
            Property.Key.HEAL_ON_VEIL,
            Property.Key.HEAL_P_CAST,
            Property.Key.HEAL_P_HEADSHOT_PCT,
            Property.Key.HEAL_P_STACK,
            Property.Key.HEALING_PER_SECOND,
            Property.Key.LIFE_DRAIN_PER_SECOND,
                -> Emoji.HEAL_LIFESTEAL

            Property.Key.HEALTH_TO_DAMAGE,
            Property.Key.HP_BONUS,
                -> Emoji.HP

            Property.Key.BONUS_HEALTH_REGEN,
            Property.Key.BONUS_HP_REG,
            Property.Key.HP_REGEN_TOTAL,
            Property.Key.OOC_HP_REG,
            Property.Key.REGEN,
                -> Emoji.HP_REGEN

            Property.Key.IMMOBILIZE_DURATION,
                -> Emoji.IMMOBILIZE

            Property.Key.BONUS_MELEE_DAMAGE_PCT,
            Property.Key.DAMAGE_HEAVY_MELEE,
                -> Emoji.MELEE_DAMAGE

            Property.Key.BONUS_MOVE_SPEED,
            Property.Key.BONUS_MS,
                -> Emoji.MOVE_SPEED

            Property.Key.MOVE_SPEED_PENALTY_PER_STACK,
            Property.Key.MOVE_SPEED_SLOW_PCT,
            Property.Key.MOVEMENT_SLOW_PCT,
            Property.Key.SLOW_DURATION,
            Property.Key.SLOW_PCT,
            Property.Key.SLOW_PERCENT,
                -> Emoji.MOVEMENT_SLOW

            Property.Key.RADIUS,
            Property.Key.TECH_RADIUS_MULT,
                -> Emoji.RADIUS

            Property.Key.TECH_RANGE_MULT,
                -> Emoji.RANGE

            Property.Key.SILENCE_DURATION,
                -> Emoji.SILENCE

            Property.Key.DAMAGE_SPIRIT,
            Property.Key.OUTGOING_TECH_DAMAGE_PERCENT,
                -> Emoji.SPIRIT_DAMAGE

            Property.Key.TECH_POWER,
            Property.Key.TECH_POWER_PCT,
                -> Emoji.SPIRIT_POWER

            Property.Key.MAGIC_RESIST_RED,
            Property.Key.TECH_RESIST,
                -> Emoji.SPIRIT_RESIST

            Property.Key.BONUS_SPRINT,
            Property.Key.STAMINA,
            Property.Key.STAMINA_CD_RED,
                -> Emoji.SPRINT_SPEED

            Property.Key.PETRIFY_DURATION,
            Property.Key.SLEEP_DURATION,
            Property.Key.STUN_DURATION,
                -> Emoji.STUN

            else -> null
        }
    }


    private companion object {
        const val TAG = "Emojifier"
    }
}
