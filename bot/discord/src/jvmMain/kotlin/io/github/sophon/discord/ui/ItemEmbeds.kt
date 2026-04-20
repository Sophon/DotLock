package io.github.sophon.discord.ui

import dev.kord.common.Color
import dev.kord.rest.builder.message.EmbedBuilder
import io.github.sophon.core.domain.model.Bonus
import io.github.sophon.core.domain.model.FeatureInfo
import io.github.sophon.core.domain.model.Item
import io.github.sophon.discord.feat.emoji.Emojifier

internal fun itemEmbed(
    item: Item,
    featureInfo: FeatureInfo,
    emojifier: Emojifier,
): EmbedBuilder.() -> Unit = {
    title = item.name
    description?.let {
        description = it
    }
    color = Color(BEIGE)
    featureFooter(featureInfo)
    item.url.image?.let { itemUrl ->
        thumbnail { url = itemUrl }
    }
    item.url.wiki?.let { wikiUrl -> url = wikiUrl }

    mandatoryField(
        name = "${emojifier.emojify(Emojifier.Emoji.SOULS)} **${item.cost}** (${item.tier})",
        value = item.description,
        inline = false,
    )
    effectsSection(item.effectSet, emojifier)
    upgradeSection(item.upgradePath)
}

private fun EmbedBuilder.effectsSection(effectSet: Set<Bonus>, emojifier: Emojifier) {
    for (effect in effectSet) {
        val title = buildString {
            var string = effect.type.toString()
            effect.cooldown?.let { string += " - ${Emojifier.Emoji.COOLDOWN_DELAY_DURATION} $it" }
            effect.chargeUp?.let { string += " - ${Emojifier.Emoji.CHANNEL_TIME} $it" }
            append(string)
        }

        val properties = buildString {
            effect.description?.let { append("$it\n") }

            for (property in effect.properties) {
                append("- **${property.key}**: ${property.value}\n")
            }
        }

        mandatoryField(
            name = title,
            value = properties,
            inline = false,
        )
    }
}

private fun EmbedBuilder.upgradeSection(upgradePath: Item.UpgradePath) {
    var index = 1

    val from = buildString {
        upgradePath.from.forEach { item ->
            appendLine("${index++}. **$item**")
        }
    }
    if (from.isNotBlank()) {
        mandatoryField(
            name = "From:",
            value = from,
        )
    }

    val to = buildString {
        upgradePath.to.forEach { item ->
            appendLine("${index++}. **$item**")
        }
    }
    if (to.isNotBlank()) {
        mandatoryField(
            name = "To:",
            value = to,
        )
    }
}


private const val BEIGE = 0x00EEDCBD