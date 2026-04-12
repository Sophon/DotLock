package io.github.sophon.discord.ui

import dev.kord.common.Color
import dev.kord.rest.builder.message.EmbedBuilder
import io.github.sophon.core.domain.model.FeatureInfo
import io.github.sophon.core.domain.model.Hero

internal fun heroEmbed(
    hero: Hero,
    featureInfo: FeatureInfo,
): EmbedBuilder.() -> Unit = {
    title = hero.name
    color = Color(BEIGE)
    featureFooter(featureInfo)

    //TODO:
}


private const val BEIGE = 0x00EEDCBD