package io.github.sophon.discord.feat.emoji.data

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header

internal class DiscordEmojiSource(
    private val client: HttpClient,
    private val appId: String,
    private val apiToken: String,
) {
    suspend fun fetch(): List<EmojiDto> {
        val response = client.get("https://discord.com/api/v10/applications/$appId/emojis") {
            header("Authorization", "Bot $apiToken")
        }
        return response.body<EmojiListDto>().items
    }
}
