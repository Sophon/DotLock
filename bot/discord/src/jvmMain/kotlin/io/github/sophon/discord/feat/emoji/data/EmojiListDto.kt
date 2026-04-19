package io.github.sophon.discord.feat.emoji.data

import kotlinx.serialization.Serializable

@Serializable
internal data class EmojiListDto(
    val items: List<EmojiDto>,
)

@Serializable
internal data class EmojiDto(
    val id: String,
    val name: String,
    val animated: Boolean,
)
