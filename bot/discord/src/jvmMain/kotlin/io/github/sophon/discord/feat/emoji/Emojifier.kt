package io.github.sophon.discord.feat.emoji

import io.github.aakira.napier.Napier
import io.github.sophon.core.domain.model.Effect
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

    fun emojify(effect: Effect): String {
        //TODO: purely relying on Type won't work, we need to combine both
        val emoji = when (effect.type) {
            "fire_rate" -> Emoji.BULLET_SPEED
            "health" -> Emoji.HP
            "healing" -> Emoji.HEAL_LIFESTEAL
            "clipsize" -> Emoji.CLIP
            "duration" -> Emoji.COOLDOWN_DELAY_DURATION
            "cooldown",
            "charge_cooldown",
                -> Emoji.ABILITY_CD

            "sprint" -> Emoji.SPRINT_SPEED
            "move_speed" -> Emoji.MOVE_SPEED

            "cast" -> Emoji.CHANNEL_TIME

            "tech_armor_up" -> Emoji.SPIRIT_RESIST
            "bullet_armor_up",
            "bullet_armor_down",
                -> Emoji.BULLET_RESIST
            "damage" -> Emoji.DAMAGE_AMPLIFY

            "tech_damage" -> Emoji.SPIRIT_DAMAGE
            "melee_damage" -> Emoji.MELEE_DAMAGE
            "bullet_damage" -> Emoji.BULLET_WEAPON_DAMAGE

            "distance",
            "range",
                -> Emoji.RANGE

            "slow" -> Emoji.MOVEMENT_SLOW

            else -> {
                if (effect.type.isNullOrBlank().not()) {
                    Napier.i(tag = TAG) { "unhandled: ${effect.type}" }
                }
                null
            }
        }

        val result = emoji?.let { emojify(it) } ?: ""

        return result
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


    private companion object {
        const val TAG = "Emojifier"
    }
}
