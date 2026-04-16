package io.github.sophon.deadlock.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
internal data class ItemDto(
    @SerialName("Key") val key: String,
    @SerialName("Name") val name: String? = null,
    @SerialName("Description") val description: String? = null,
    @SerialName("Cost") val cost: Int? = null,
    @SerialName("Tier") val tier: Int? = null,
    @SerialName("Slot") val slot: String? = null,
    @SerialName("Activation") val activation: String,
    @SerialName("Components") val components: List<String>? = null,
    @SerialName("TargetTypes") val targetTypes: List<String>? = null,
    @SerialName("ShopFilters") val shopFilters: List<String>? = null,
    @SerialName("IsDisabled") val isDisabled: Boolean?,
    @SerialName("StreetBrawl") val streetBrawl: Boolean?,
    @SerialName("Info1") val info1: ItemInfoDto? = null,
    @SerialName("Info2") val info2: ItemInfoDto? = null,
    @SerialName("Info3") val info3: ItemInfoDto? = null,
    @SerialName("Info4") val info4: ItemInfoDto? = null,
    @SerialName("Upgrades") val upgrades: Map<String, JsonElement>? = null,
    @SerialName("Other") val other: Map<String, OtherDto>? = null,
)

@Serializable
internal data class ItemInfoDto(
    @SerialName("Type") val type: String? = null,
    @SerialName("DescKey") val descKey: String? = null,
    @SerialName("Cooldown") val cooldown: Double? = null,
    @SerialName("ChargeUp") val chargeUp: Double? = null,
    @SerialName("Main") val main: List<ItemPropDto> = emptyList(),
    @SerialName("Alt") val alt: List<ItemPropDto> = emptyList(),
)

@Serializable
internal data class ItemPropDto(
    @SerialName("Title") val title: String? = null,
    @SerialName("Key") val key: String,
    @SerialName("Value") val value: JsonElement,
    @SerialName("Scale") val scaleDto: ScaleDto? = null,
    @SerialName("Type") val type: String? = null,
    @SerialName("UsageFlags") val usageFlags: String? = null,
)

@Serializable
internal data class OtherDto(
    @SerialName("Key") val key: String,
    @SerialName("Value") val value: JsonElement? = null,
    @SerialName("Type") val type: String? = null,
)
