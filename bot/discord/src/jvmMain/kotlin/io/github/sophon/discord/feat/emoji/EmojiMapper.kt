package io.github.sophon.discord.feat.emoji

internal fun EmojiDto.toFormatted(): String {
    val prefix = if (animated) "<a" else "<"
    return "$prefix:$name:$id>"
}
