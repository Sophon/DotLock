package io.github.sophon.discord.ui

import dev.kord.common.Color
import dev.kord.rest.builder.message.EmbedBuilder
import io.github.sophon.core.domain.model.Ability
import io.github.sophon.core.domain.model.FeatureInfo
import io.github.sophon.core.domain.model.Effect
import io.github.sophon.core.util.toTitleCase
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

    castSection(ability.castMap, emojifier)
    ability.description?.let {
        mandatoryField(value = it, inline = false)
    }
    effectsSection(ability.effectSet, emojifier)
}


private fun EmbedBuilder.castSection(castMap: Map<String, Effect>, emojifier: Emojifier) {
    val lines = buildList {
        castMap.forEach { (key, effect) ->
            add("- ${emojifier.emojify(effect)} **${key}**: ${effect.value}")
        }
    }

    if (lines.isEmpty()) return

    val mid = (lines.size + 1) / 2
    mandatoryField(value = lines.take(mid).joinToString("\n"))
    mandatoryField(value = lines.drop(mid).joinToString("\n"))
}

private fun EmbedBuilder.effectsSection(effectSet: Set<Effect>, emojifier: Emojifier) {
    val string = buildString {
        effectSet.forEach { effect ->
            append("- ${emojifier.emojify(effect)} **${effect.key.toTitleCase()}**: ${effect.value}\n")
        }
    }

    if (string.isBlank()) return

    mandatoryField(value = string, inline = false)
}


private const val BEIGE = 0x00EEDCBD