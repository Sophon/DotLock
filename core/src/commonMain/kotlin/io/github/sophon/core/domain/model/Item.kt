package io.github.sophon.core.domain.model

import io.github.sophon.core.util.toEnumOrDefault

data class Item(
    val key: String,
    val name: String,
    val url: Url,

    val cost: Int,
    val componentList: List<String>,
    val shopFilterList: List<ShopFilter>,

    val description: String,
    val slot: Slot,
    val activation: Activation,
    val targetTypeList: List<String>,
    val cooldown: Double,
    val chargeUp: Double,

    val mainPropertyList: List<Property>,
    val altPropertyList: List<Property>,
    val upgradeList: List<Property>,

    val bonusSet: Set<Bonus>,
) {
    enum class Activation {
        PASSIVE,
        INSTANT_CAST,
        INSTANT_CAST_TOGGLE,
        PRESS,
        ON_RELEASE,
        UNKNOWN,
    }

    enum class Slot {
        WEAPON,
        ARMOR,
        TECH,
        UNKNOWN,
    }

    data class Property(
        val key: String,
        val value: String,
        val type: String? = null,
        val scale: Scale? = null,
    )

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
