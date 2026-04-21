package io.github.sophon.deadlock.remote.mapper

import io.github.aakira.napier.Napier
import io.github.sophon.core.domain.model.ActivationType
import io.github.sophon.core.domain.model.Bonus
import io.github.sophon.core.domain.model.Item
import io.github.sophon.core.domain.model.Effect
import io.github.sophon.core.domain.model.Scale
import io.github.sophon.core.domain.model.ScaledValue
import io.github.sophon.core.domain.model.Url
import io.github.sophon.core.util.cleanDescription
import io.github.sophon.core.util.cleanHtml
import io.github.sophon.core.util.formKey
import io.github.sophon.core.util.toSnakeCase
import io.github.sophon.deadlock.DeadlockFeatureInfo
import io.github.sophon.deadlock.remote.dto.ItemDto
import io.github.sophon.deadlock.remote.dto.ItemInfoDto
import io.github.sophon.deadlock.remote.dto.ItemPropDto
import kotlinx.serialization.json.doubleOrNull
import kotlinx.serialization.json.jsonPrimitive

internal fun Map.Entry<String, ItemDto>.toDomain(
    imageUrlMap: Map<String, String>,
    descriptionMap: Map<String, String>,
): Item {
    val dto = value
    val key = key

    val item = dto.toDomain(key, imageUrlMap, descriptionMap)

    return item
}

internal fun ItemDto.toDomain(
    key: String,
    imageUrlMap: Map<String, String>,
    descriptionMap: Map<String, String>,
): Item {
    val itemKey = (name?.formKey() ?: key)

    val item = Item(
        key = itemKey,
        name = name ?: key,
        url = Url(
            image = imageUrlMap[itemKey],
            wiki = name.toWikiUrl(),
        ),
        aliasList = listOf(key),

        cost = cost ?: 0,
        tier = tier ?: 0,
        upgradePath = Item.UpgradePath(
            from = components ?: emptyList(),
            to = emptyList(), //TODO: figure out how to do upgradeTo
        ),
        shopFilterList = shopFilters.orEmpty().map { filter ->
            Item.ShopFilter.fromString(filter)
        },

        description = description.orEmpty()
            .cleanHtml()
            .cleanDescription(),
        slot = slot.toDomainSlot(),
        activation = activation.toDomainActivation(),
        targetTypeList = targetTypes.orEmpty(),

        bonusSet = listOf(
            info1, info2, info3, info4
        ).toDomainBonusList(descriptionMap),
    )

    return item
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

private fun List<ItemInfoDto?>.toDomainBonusList(descriptionMap: Map<String, String>): Set<Bonus> {
    return filterNotNull().map { dto ->
        val descriptionKey = dto.descKey.orEmpty().replace("#", "")
        Bonus(
            type = dto.type.toActivationType(),
            description = descriptionMap[descriptionKey]?.cleanDescription(),
            cooldown = dto.cooldown,
            chargeUp = dto.chargeUp,
            effectList = (dto.main + dto.alt).toDomainPropertyList(),
        )
    }.toSet()
}

private fun String?.toActivationType(): ActivationType {
    return when (this) {
        "Innate" -> ActivationType.INNATE
        "Passive" -> ActivationType.PASSIVE
        "Active" -> ActivationType.ACTIVE
        else -> ActivationType.UNKNOWN
    }
}

private fun List<ItemPropDto>.toDomainPropertyList(): List<Effect> {
    return map { dto ->
        Effect(
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