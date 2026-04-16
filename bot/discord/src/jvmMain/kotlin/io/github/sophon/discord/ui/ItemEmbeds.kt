package io.github.sophon.discord.ui

import dev.kord.common.Color
import dev.kord.rest.builder.message.EmbedBuilder
import io.github.sophon.core.domain.model.Bonus
import io.github.sophon.core.domain.model.FeatureInfo
import io.github.sophon.core.domain.model.Item
import io.github.sophon.core.util.getEmptyChar
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

    bonusSection(bonusList = item.bonusList, emojifier)
}

private fun EmbedBuilder.bonusSection(bonusList: List<Bonus>, emojifier: Emojifier) {
    for (bonus in bonusList) {
        val title = buildString {
            var string = bonus.type.toString()
            bonus.cooldown?.let { string += " - ${Emojifier.Emoji.COOLDOWN_DELAY_DURATION} $it" }
            bonus.chargeUp?.let { string += " - ${Emojifier.Emoji.CHANNEL_TIME} $it" }
            append(string)
        }

        val properties = buildString {
            bonus.descKey?.let { append("$it\n") }

            for (property in bonus.properties) {
                append("- ${emojifier.emojify(property)} ${property.key}: ${property.value}\n")
            }
        }

        mandatoryField(
            name = title,
            value = properties,
            inline = false,
        )
    }
}


private const val BEIGE = 0x00EEDCBD