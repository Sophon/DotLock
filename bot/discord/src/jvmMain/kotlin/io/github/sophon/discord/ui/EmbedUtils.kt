package io.github.sophon.discord.ui

import dev.kord.rest.builder.message.EmbedBuilder
import io.github.sophon.core.domain.model.FeatureInfo
import io.github.sophon.core.util.orDash
import io.github.sophon.core.util.truncate
import io.github.sophon.discord.EMBED_MAX_LENGTH

internal fun EmbedBuilder.mandatoryField(
    value: String?,
    name: String? = null,
    inline: Boolean = true,
    escapeAsterisks: Boolean = false,
) {
    val formatted = value
        .orDash()
        .let { value ->
            if (escapeAsterisks) value.replace("*", "\\*")
            else value
        }
        .truncate(EMBED_MAX_LENGTH)

    field {
        this.name = name ?: ""
        this.value = formatted
        this.inline = inline
    }
}

internal fun EmbedBuilder.optionalField(
    name: String,
    value: String?,
    inline: Boolean = true,
    escapeAsterisks: Boolean = false,
) {
    val formatted = value
        .orDash()
        .let { value ->
            if (escapeAsterisks) value.replace("*", "\\*")
            else value
        }
        .truncate(EMBED_MAX_LENGTH)

    if (value.isNullOrBlank().not()) {
        field {
            this.name = name
            this.value = formatted
            this.inline = inline
        }
    }
}

internal fun EmbedBuilder.featureFooter(featureInfo: FeatureInfo) {
    footer {
        text = featureInfo.name
        icon = featureInfo.iconUrl
    }
}