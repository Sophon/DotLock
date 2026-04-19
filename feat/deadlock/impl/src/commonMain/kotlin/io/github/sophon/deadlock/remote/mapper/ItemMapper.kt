package io.github.sophon.deadlock.remote.mapper

import io.github.aakira.napier.Napier
import io.github.sophon.core.domain.model.ActivationType
import io.github.sophon.core.domain.model.Bonus
import io.github.sophon.core.domain.model.Property
import io.github.sophon.core.domain.model.Item
import io.github.sophon.core.domain.model.Scale
import io.github.sophon.core.domain.model.ScaledValue
import io.github.sophon.core.domain.model.Url
import io.github.sophon.core.util.cleanHtml
import io.github.sophon.core.util.cleanItemDescription
import io.github.sophon.core.util.formKey
import io.github.sophon.core.util.toSnakeCase
import io.github.sophon.deadlock.DeadlockFeatureInfo
import io.github.sophon.deadlock.remote.dto.ItemDto
import io.github.sophon.deadlock.remote.dto.ItemInfoDto
import io.github.sophon.deadlock.remote.dto.ItemPropDto
import kotlinx.serialization.json.doubleOrNull
import kotlinx.serialization.json.jsonPrimitive
import kotlin.collections.map
import kotlin.collections.orEmpty

internal fun Map.Entry<String, ItemDto>.toDomain(imageUrls: Map<String, String>): Item {
    val dto = value
    val key = key

    val item = dto.toDomain(key, imageUrls)

    return item
}

internal fun ItemDto.toDomain(
    key: String,
    imageUrls: Map<String, String>,
): Item {
    val itemKey = (name?.formKey() ?: key)

    return Item(
        key = itemKey,
        name = name ?: key,
        url = Url(
            image = imageUrls[itemKey],
            wiki = name.toWikiUrl(),
        ),

        cost = cost ?: 0,
        tier = tier ?: 0,
        componentList = components.orEmpty(),
        shopFilterList = shopFilters.orEmpty().map { filter ->
            Item.ShopFilter.fromString(filter)
        },

        description = description.orEmpty()
            .cleanHtml()
            .cleanItemDescription(),
        slot = slot.toDomainSlot(),
        activation = activation.toDomainActivation(),
        targetTypeList = targetTypes.orEmpty(),

        bonusList = listOf(
            info1, info2, info3, info4
        ).toDomainBonusList(),
    )
}


private fun String?.toDomainSlot(): Item.Slot {
    return when (this) {
        "Weapon" -> Item.Slot.WEAPON
        "Armor" -> Item.Slot.ARMOR
        "Tech" -> Item.Slot.TECH
        else -> {
            Napier.e(tag = TAG) { "slot: $this" }
            Item.Slot.UNKNOWN
        }
    }
}

private fun String?.toDomainActivation(): Item.Activation {
    return when (this) {
        "Passive" -> Item.Activation.PASSIVE
        "InstantCast" -> Item.Activation.INSTANT_CAST
        "InstantCastToggle" -> Item.Activation.INSTANT_CAST_TOGGLE
        "Press" -> Item.Activation.PRESS
        "OnRelease" -> Item.Activation.ON_RELEASE
        else -> {
            Napier.e(tag = TAG) { "activation: $this" }
            Item.Activation.UNKNOWN
        }
    }
}

private fun String?.toScaleType(): ScaledValue.ScaleType {
    return try {
        ScaledValue.ScaleType.valueOf(this?.uppercase() ?: return ScaledValue.ScaleType.SPIRIT)
    } catch (_: IllegalArgumentException) {
        ScaledValue.ScaleType.SPIRIT
    }
}

private fun List<ItemInfoDto?>.toDomainBonusList(): List<Bonus> {
    return filterNotNull().map { dto ->
        Bonus(
            type = dto.type.toActivationType(),
            descKey = dto.descKey,
            cooldown = dto.cooldown,
            chargeUp = dto.chargeUp,
            properties = (dto.main + dto.alt).toDomainPropertyList(),
        )
    }
}

private fun String?.toActivationType(): ActivationType {
    return when (this) {
        "Innate" -> ActivationType.INNATE
        "Passive" -> ActivationType.PASSIVE
        "Active" -> ActivationType.ACTIVE
        else -> ActivationType.UNKNOWN
    }
}

private fun List<ItemPropDto>.toDomainPropertyList(): List<Property> {
    return map { dto ->
        Property(
            key = dto.key.toSnakeCase(),
            value = ScaledValue(
                value = dto.value.jsonPrimitive.doubleOrNull ?: 0.0,
                scale = dto.scaleDto?.let {
                    Scale(
                        value = it.value ?: 0.0,
                        type = it.type.toScaleType(),
                    )
                },
            ),
            type = dto.type ?: "",
        )
    }
}


private fun String?.toWikiUrl(): String? {
    if (this == null) return null
    val result = "${DeadlockFeatureInfo.featureInfo.url}/${this.replace(" ", "_")}"
    return result
}


private const val TAG = "ItemMapper"