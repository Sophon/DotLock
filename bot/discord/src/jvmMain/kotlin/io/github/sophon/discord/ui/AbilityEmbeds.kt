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

    //TODO:
}


private const val BEIGE = 0x00EEDCBD