package io.github.sophon.discord.ui

import dev.kord.common.Color
import dev.kord.rest.builder.message.EmbedBuilder
import io.github.sophon.core.domain.model.Ability
import io.github.sophon.core.domain.model.Bonus
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

    propertiesSection(ability.property, emojifier)
    bonusSection(ability.bonusSet, emojifier)
}


private fun EmbedBuilder.propertiesSection(property: Ability.Property, emojifier: Emojifier) {
    val lines = buildList {
        property.channelTime?.let { add("- ${emojifier.emojify(Emojifier.Emoji.CHANNEL_TIME)} **Channel time** $it") }
        property.chargeCount?.let { add("- ${emojifier.emojify(Emojifier.Emoji.CHANNEL_TIME)} **Charge count** $it") }
        property.chargeCooldown?.let { add("- ${emojifier.emojify(Emojifier.Emoji.CHARGE_COOLDOWN)} **Charge cooldown** $it") }
        property.cooldown?.let { add("- ${emojifier.emojify(Emojifier.Emoji.COOLDOWN_DELAY_DURATION)} **Cooldown** $it") }
        property.castRange?.let { add("- ${emojifier.emojify(Emojifier.Emoji.RANGE)} **Cast range** $it") }
        property.duration?.let { add("- ${emojifier.emojify(Emojifier.Emoji.COOLDOWN_DELAY_DURATION)} **Duration** $it") }
        property.radius?.let { add("- ${emojifier.emojify(Emojifier.Emoji.RADIUS)} **Radius** $it") }
    }

    if (lines.isEmpty()) return

    val mid = (lines.size + 1) / 2
    mandatoryField(name = getEmptyChar(), value = lines.take(mid).joinToString("\n"))
    mandatoryField(name = getEmptyChar(), value = lines.drop(mid).joinToString("\n"))
}

private fun EmbedBuilder.bonusSection(bonusSet: Set<Bonus>, emojifier: Emojifier) {
    val string = buildString {
        bonusSet.forEach { bonus ->
            append("- ${emojifier.emojify(bonus)} **${bonus.type}**: ${bonus.value}\n")
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