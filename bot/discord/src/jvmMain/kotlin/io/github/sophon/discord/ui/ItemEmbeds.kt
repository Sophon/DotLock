package io.github.sophon.discord.ui

import dev.kord.common.Color
import dev.kord.rest.builder.message.EmbedBuilder
import io.github.sophon.core.domain.model.FeatureInfo
import io.github.sophon.core.domain.model.Item

internal fun itemEmbed(
    item: Item,
    featureInfo: FeatureInfo,
): EmbedBuilder.() -> Unit = {
    title = item.name
    description?.let {
        description = it
    }
    color = Color(BEIGE)
    featureFooter(featureInfo)

    mandatoryField(
        name = "Tier",
        value = item.shop.tier?.toString(),
    )
    mandatoryField(
        name = "Cost",
        value = item.shop.cost?.toString(),
    )
    mandatoryField(
        name = "Activation",
        value = item.shop.activation.name,
    )

    createBonuses(item.bonusSet)
}


private fun EmbedBuilder.createBonuses(bonusSet: Set<Item.Bonus>) {
    val formatted = bonusSet.joinToString("\n") { bonus ->
        val scaleSuffix = bonus.value.scale?.let { " (${it.type.name})" } ?: ""
        "${bonus.type.name}: ${bonus.value.value}$scaleSuffix"
    }.ifEmpty { null }

    optionalField(
        name = "Bonuses",
        value = formatted,
        inline = false,
    )
}


private const val BEIGE = 0x00EEDCBD