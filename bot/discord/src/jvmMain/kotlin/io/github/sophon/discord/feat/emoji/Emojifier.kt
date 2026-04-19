package io.github.sophon.discord.feat.emoji

import io.github.aakira.napier.Napier
import io.github.sophon.core.domain.model.Property
import io.github.sophon.core.domain.model.ScaledValue
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
        //TODO: implement String.emojify(): Emoji

        return property.type ?: ""
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
