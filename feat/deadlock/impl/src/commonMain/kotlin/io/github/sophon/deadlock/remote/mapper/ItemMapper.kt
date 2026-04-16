package io.github.sophon.deadlock.remote.mapper

import io.github.sophon.core.domain.model.Bonus
import io.github.sophon.core.domain.model.Item
import io.github.sophon.core.domain.model.Scale
import io.github.sophon.core.domain.model.ScaledValue
import io.github.sophon.core.domain.model.Url
import io.github.sophon.core.util.formKey
import io.github.sophon.deadlock.DeadlockFeatureInfo
import io.github.sophon.deadlock.remote.dto.ItemDto
import io.github.sophon.deadlock.remote.dto.ItemPropDto
import io.github.sophon.deadlock.remote.dto.OtherDto
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.doubleOrNull
import kotlinx.serialization.json.jsonPrimitive
import kotlin.collections.map
import kotlin.collections.orEmpty

internal fun Map.Entry<String, ItemDto>.toDomain(imageUrls: Map<String, String>): Item {
    val dto = value
    val key = key
    val itemKey = (value.name?.formKey() ?: key)

    val item = Item(
        key = itemKey,
        name = dto.name ?: key,
        url = Url(
            image = imageUrls[itemKey],
            wiki = dto.name.toWikiUrl(),
        ),

        cost = dto.cost ?: 0,
        componentList = dto.components.orEmpty(),
        shopFilterList = dto.shopFilters.orEmpty().map { filter ->
            Item.ShopFilter.fromString(filter)
        },

        description = dto.description.orEmpty(),
        slot = dto.slot.toDomainSlot(),
        activation = dto.activation.toDomainActivation(),
        targetTypeList = dto.targetTypes.orEmpty(),
        cooldown = dto.info1?.cooldown ?: 0.0,
        chargeUp = dto.info1?.chargeUp ?: 0.0,

        mainPropertyList = dto.info1?.main.toDomainPropertyList(),
        altPropertyList = dto.info1?.alt.toDomainPropertyList(),
        upgradeList = dto.upgrades.toDomainUpgradeList(),

        bonusSet = dto.other.toDomainBonusSet(),
    )

    return item
}


private fun String?.toDomainSlot(): Item.Slot {
    return when (this) {
        "Weapon" -> Item.Slot.WEAPON
        "Armor" -> Item.Slot.ARMOR
        "Tech" -> Item.Slot.TECH
        else -> Item.Slot.UNKNOWN
    }
}

private fun String?.toDomainActivation(): Item.Activation {
    return when (this) {
        "Passive" -> Item.Activation.PASSIVE
        "InstantCast" -> Item.Activation.INSTANT_CAST
        "InstantCastToggle" -> Item.Activation.INSTANT_CAST_TOGGLE
        "Press" -> Item.Activation.PRESS
        "OnRelease" -> Item.Activation.ON_RELEASE
        else -> Item.Activation.UNKNOWN
    }
}

private fun List<ItemPropDto?>?.toDomainPropertyList(): List<Item.Property> {
    return this?.filterNotNull()?.map { dto ->
        Item.Property(
            key = dto.key,
            value = dto.value.jsonPrimitive.content,
            type = dto.type,
            scale = dto.scaleDto?.let {
                Scale(
                    value = it.value ?: 0.0,
                    type = it.type.toScaleType(),
                )
            },
        )
    } ?: emptyList()
}

private fun String?.toScaleType(): ScaledValue.ScaleType {
    return try {
        ScaledValue.ScaleType.valueOf(this?.uppercase() ?: return ScaledValue.ScaleType.SPIRIT)
    } catch (_: IllegalArgumentException) {
        ScaledValue.ScaleType.SPIRIT
    }
}

private fun Map<String, JsonElement>?.toDomainUpgradeList(): List<Item.Property> {
    return this?.map { (key, value) ->
        Item.Property(
            key = key,
            value = value.jsonPrimitive.content,
            type = null,
            scale = null,
        )
    } ?: emptyList()
}

private fun Map<String, OtherDto>?.toDomainBonusSet(): Set<Bonus> {
    return this?.map { (_, dto) ->
        val type = dto.key.toSnakeCase().toBonusType()
        val value = dto.value.jsonPrimitive.doubleOrNull ?: 0.0
        Bonus(
            type = type,
            value = ScaledValue(value = value, scale = null),
        )
    }?.toSet() ?: emptySet()
}

private fun String.toSnakeCase(): String {
    return replace(Regex("([a-z])([A-Z])")) { "${it.groupValues[1]}_${it.groupValues[2]}" }
        .replace(Regex("([A-Z]+)([A-Z][a-z])")) { "${it.groupValues[1]}_${it.groupValues[2]}" }
        .uppercase()
}

private fun String.toBonusType(): Bonus.Type {
    return try {
        Bonus.Type.valueOf(this)
    } catch (_: IllegalArgumentException) {
        Bonus.Type.UNKNOWN
    }
}

private fun String?.toWikiUrl(): String? {
    if (this == null) return null
    val result = "${DeadlockFeatureInfo.featureInfo.url}/${this.replace(" ", "_")}"
    return result
}
