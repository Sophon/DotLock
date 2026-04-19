package io.github.sophon.discord.ui

import dev.kord.common.Color
import dev.kord.rest.builder.message.EmbedBuilder
import io.github.sophon.core.domain.model.Ability
import io.github.sophon.core.domain.model.Property
import io.github.sophon.core.domain.model.FeatureInfo
import io.github.sophon.core.util.getEmptyChar
import io.github.sophon.discord.feat.emoji.Emojifier

internal fun abilityEmbed(
    ability: Ability,
    featureInfo: FeatureInfo,
    emojifier: Emojifier,
): EmbedBuilder.() -> Unit = {
    title = ability.name
    color = Color(BEIGE)
    featureFooter(featureInfo)
    ability.imageUrl?.let { abilityUrl ->
        thumbnail { url = abilityUrl }
    }

    propertiesSection(ability.propertyMap, emojifier)
    bonusSection(ability.effectsSet, emojifier)
}


private fun EmbedBuilder.propertiesSection(propertyMap: Map<String, Property>, emojifier: Emojifier) {
    val lines = buildList {
        propertyMap.forEach { (key, property) ->
            add("- **${key}**: ${property.value}")
        }
    }

    if (lines.isEmpty()) return

    val mid = (lines.size + 1) / 2
    mandatoryField(name = "", value = lines.take(mid).joinToString("\n"))
    mandatoryField(name = "", value = lines.drop(mid).joinToString("\n"))
}

private fun EmbedBuilder.bonusSection(bonusSet: Set<Property>, emojifier: Emojifier) {
    val string = buildString {
        bonusSet.forEach { bonus ->
            append("- **${bonus.key}**: ${bonus.value}\n")
        }
    }

    if (string.isBlank()) return

    mandatoryField(
        name = "",
        value = string,
        inline = false,
    )
}


private const val BEIGE = 0x00EEDCBD