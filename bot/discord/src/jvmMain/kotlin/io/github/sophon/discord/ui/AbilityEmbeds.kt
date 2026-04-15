package io.github.sophon.discord.ui

import dev.kord.common.Color
import dev.kord.rest.builder.message.EmbedBuilder
import io.github.sophon.core.domain.model.Ability
import io.github.sophon.core.domain.model.FeatureInfo

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

    targetingSection(ability.targeting)
    timingSection(ability.timing)
    propertiesField(ability.propertySet, ability.upgrades)
}


private fun EmbedBuilder.targetingSection(targeting: Ability.Targeting) {
    optionalField(
        name = "${Emoji.RANGE} Cast range",
        value = targeting.castRange?.value?.toString(),
    )
    optionalField(
        name = "Unit targets",
        value = targeting.unitTargetLimit?.toString()
    )
}

private fun EmbedBuilder.timingSection(timing: Ability.Timing) {
    mandatoryField(
        name = "${Emoji.COOLDOWN_ACTIVE} CD",
        value = timing.cooldown?.value?.toString(),
    )

    if (timing.channelTime?.value != null) {
        optionalField(
            name = "Channel",
            value = "${timing.channelTime?.value} (ms: ${timing.channelMoveSpeed})"
        )
    }

    optionalField(
        name = "Delay",
        value = timing.castDelay?.toString()
    )
    optionalField(
        name = "${Emoji.CHARGE} Charges",
        value = timing.charges?.toString()
    )
}

private fun EmbedBuilder.propertiesField(
    propertySet: Set<Ability.Property>,
    upgradeList: List<Ability.Upgrade>,
) {
    val bonuses = buildString {
        propertySet.forEach { property ->
            append("- ${property.type.name}: ${property.value.value}\n")
        }
        upgradeList.forEach { upgrade ->
            val formattedValue: Double = when (val v = upgrade.changes.second) {
                is Ability.Upgrade.UpgradeValue.Plain -> v.value
                is Ability.Upgrade.UpgradeValue.Scaled -> v.scaledValue.value
            }
            append("- ${upgrade.changes.first}: $formattedValue")
        }
    }

    mandatoryField(
        name = "Bonuses",
        value = bonuses,
        inline = false,
    )
}


private const val BEIGE = 0x00EEDCBD