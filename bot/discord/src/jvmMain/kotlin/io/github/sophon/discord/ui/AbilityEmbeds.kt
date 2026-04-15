package io.github.sophon.discord.ui

import dev.kord.common.Color
import dev.kord.rest.builder.message.EmbedBuilder
import io.github.sophon.core.domain.model.Ability
import io.github.sophon.core.domain.model.Bonus
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
        property.channelTime?.let { add("- ${Emoji.CHANNEL_TIME} **Channel time** $it") }
        property.chargeCount?.let { add("- ${Emoji.CHANNEL_TIME} **Charge count** $it") }
        property.chargeCooldown?.let { add("- ${Emoji.CHARGE_COOLDOWN} **Charge cooldown** $it") }
        property.cooldown?.let { add("- ${Emoji.COOLDOWN_DELAY_DURATION} **Cooldown** $it") }
        property.castRange?.let { add("- ${Emoji.RANGE} **Cast range** $it") }
        property.duration?.let { add("- ${Emoji.COOLDOWN_DELAY_DURATION} **Duration** $it") }
        property.radius?.let { add("- ${Emoji.RADIUS} **Radius** $it") }
    }

    if (lines.isEmpty()) return

    val mid = (lines.size + 1) / 2
    mandatoryField(name = getEmptyChar(), value = lines.take(mid).joinToString("\n"))
    mandatoryField(name = getEmptyChar(), value = lines.drop(mid).joinToString("\n"))
}

private fun EmbedBuilder.bonusSection(bonusSet: Set<Bonus>) {
    val string = buildString {
        bonusSet.forEach { bonus ->
            append("- ${bonus.emojify()} **${bonus.type}**: ${bonus.value}\n")
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