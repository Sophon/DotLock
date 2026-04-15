package io.github.sophon.discord.ui

import dev.kord.common.Color
import dev.kord.rest.builder.message.EmbedBuilder
import io.github.sophon.core.domain.model.Bonus
import io.github.sophon.core.domain.model.FeatureInfo
import io.github.sophon.core.domain.model.Item
import io.github.sophon.core.util.getEmptyChar
import io.github.sophon.discord.feat.emoji.Emojifier
import kotlin.collections.forEach

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

    generalSection(item, emojifier)
    item.description?.let { description ->
        mandatoryField(
            name = getEmptyChar(),
            value = description,
            inline = false,
        )
    }
    bonusSection(item.bonusSet, emojifier)
}

private fun EmbedBuilder.generalSection(item: Item, emojifier: Emojifier) {
    mandatoryField(
        name = "Cost (tier)",
        value = "${emojifier.emojify(Emojifier.Emoji.SOULS)} **${item.shopInfo.cost}** (${item.shopInfo.tier})"
    )

    val timing = item.timing ?: return

    val lines = buildList {
        timing.cooldown?.let { add("- ${emojifier.emojify(Emojifier.Emoji.COOLDOWN_DELAY_DURATION)} **Cooldown** $it") }
        timing.cooldownBetweenCharge?.let { add("- ${emojifier.emojify(Emojifier.Emoji.CHARGE_COOLDOWN)} **Charge cooldown** $it") }
        timing.castDelay?.let { add("- ${emojifier.emojify(Emojifier.Emoji.COOLDOWN_DELAY_DURATION)} **Cast delay** $it") }
        timing.postCastDuration?.let { add("- ${emojifier.emojify(Emojifier.Emoji.COOLDOWN_DELAY_DURATION)} **Post cast duration** $it") }
        timing.channelTime?.let { add("- ${emojifier.emojify(Emojifier.Emoji.CHANNEL_TIME)} **Channel time** $it") }
        timing.channelMoveSpeed?.let { add("- ${emojifier.emojify(Emojifier.Emoji.MOVE_SPEED)} **Channel move speed** $it") }
        timing.duration?.let { add("- ${emojifier.emojify(Emojifier.Emoji.COOLDOWN_DELAY_DURATION)} **Duration** $it") }
        timing.charges?.let { add("- ${emojifier.emojify(Emojifier.Emoji.CHARGE_COOLDOWN)} **Charges** $it") }
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