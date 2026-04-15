package io.github.sophon.core.domain.model

import io.github.sophon.core.util.toEnumOrDefault

data class Item(
    val key: String,
    val altKey: String,
    val name: String,
    val url: Url,

    val description: String?,
    val isStreetBrawl: Boolean,

    val shopInfo: ShopInfo,
    val timing: Timing?,
    val targeting: Targeting?,

    val bonusSet: Set<Bonus>,
) {
    data class Timing(
        val cooldown: ScaledValue?,
        val cooldownBetweenCharge: ScaledValue?,
        val castDelay: ScaledValue?,
        val postCastDuration: ScaledValue?,
        val channelTime: ScaledValue?,
        val channelMoveSpeed: ScaledValue?,
        val duration: ScaledValue?,
        val charges: ScaledValue?,
    )

    data class Targeting(
        val castRange: ScaledValue?,
        val unitTargetLimit: ScaledValue?,
    )

    data class ShopInfo(
        val cost: Int?,
        val tier: Int?,
        val slot: Slot?,
        val activation: Activation,
        val targetTypeSet: Set<TargetType>,
        val shopFilters: List<ShopFilter>,
        val components: List<String>,
    ) {
        enum class Slot {
            WEAPON,
            ARMOR,
            TECH,

            UNKNOWN,
        }

        enum class Activation {
            PASSIVE,
            INSTANT_CAST,
            INSTANT_CAST_TOGGLE,
            PRESS,
            ON_RELEASE,

            UNKNOWN,
        }

        enum class TargetType {
            HERO,
            HERO_FRIENDLY,
            HERO_ENEMY,
            ALL_FRIENDLY,
            ALL_ENEMY,
            BOSS_ENEMY,
            TROOPER_FRIENDLY,
            TROOPER_ENEMY,
            MINION_FRIENDLY,
            MINION_ENEMY,
            CREEP_ENEMY,
            PROP_ENEMY,
            NEUTRAL,

            UNKNOWN;

            companion object {
                fun fromString(value: String?) = value.toEnumOrDefault(UNKNOWN)
            }
        }

        enum class ShopFilter {
            WEAPON_DAMAGE,
            MAGIC_DAMAGE,
            FIRE_RATE,
            CLIP_SIZE,
            MOVEMENT,
            DURABILITY,
            DISRUPTION,
            HEALING,
            MELEE,

            UNKNOWN;

            companion object {
                fun fromString(value: String?) = value.toEnumOrDefault(UNKNOWN)
            }
        }
    }
}
