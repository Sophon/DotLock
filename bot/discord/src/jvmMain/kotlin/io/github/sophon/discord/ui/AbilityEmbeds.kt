package io.github.sophon.discord.ui

import dev.kord.common.Color
import dev.kord.rest.builder.message.EmbedBuilder
import io.github.sophon.core.domain.model.Ability
import io.github.sophon.core.domain.model.FeatureInfo
import io.github.sophon.core.util.getEmptyChar

internal fun abilityEmbed(
    ability: Ability,
    featureInfo: FeatureInfo,
): EmbedBuilder.() -> Unit = {
    title = ability.name
    color = Color(BEIGE)
    featureFooter(featureInfo)
    ability.imageUrl?.let { abilityUrl ->
        thumbnail { url = abilityUrl }
    }

    propertiesSection(ability.property)
    bonusSection(ability.bonusSet)
}


private fun EmbedBuilder.propertiesSection(property: Ability.Property) {
    val lines = buildList {
        property.channelTime?.let { add("- ${Emoji.CHANNEL} **Channel time** $it") }
        property.chargeCount?.let { add("- ${Emoji.CHARGE} **Charge count** $it") }
        property.chargeCooldown?.let { add("- ${Emoji.CHARGE} **Charge cooldown** $it") }
        property.cooldown?.let { add("- ${Emoji.COOLDOWN} **Cooldown** $it") }
        property.castRange?.let { add("- ${Emoji.CAST_RANGE} **Cast range** $it") }
        property.duration?.let { add("- ${Emoji.DURATION} **Duration** $it") }
        property.radius?.let { add("- ${Emoji.RADIUS} **Radius** $it") }
    }

    if (lines.isEmpty()) return

    val mid = (lines.size + 1) / 2
    mandatoryField(name = getEmptyChar(), value = lines.take(mid).joinToString("\n"))
    mandatoryField(name = getEmptyChar(), value = lines.drop(mid).joinToString("\n"))
}

private fun EmbedBuilder.bonusSection(bonusSet: Set<Ability.Bonus>) {
    val string = buildString {
        bonusSet.forEach { bonus ->
            append("- **${bonus.type}**: ${bonus.value}\n")
        }
    }

    if (string.isBlank()) return

    mandatoryField(
        name = "Bonus",
        value = string,
        inline = false,
    )
}


private const val BEIGE = 0x00EEDCBD