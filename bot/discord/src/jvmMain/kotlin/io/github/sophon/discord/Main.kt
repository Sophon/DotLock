package io.github.sophon.discord

import dev.kord.core.Kord
import io.github.aakira.napier.Antilog
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.LogLevel
import io.github.aakira.napier.Napier
import io.github.sophon.core.util.maskSecret
import io.github.sophon.discord.config.DiscordConfig
import io.ktor.client.HttpClient
import io.ktor.client.engine.java.Java
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.websocket.WebSockets
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import org.koin.java.KoinJavaComponent.getKoin
import java.io.EOFException

suspend fun main() = coroutineScope {
    initLogging()
    val config = getConfig()
    val kord = createKord(config)
    initKoin(kord, config)

    val discordBot = getKoin().get<DiscordBot>()

    launch {
        discordBot.startSession()
    }.join()
}

private fun initLogging() {
    if (isDebugBuild()) {
        Napier.base(DebugAntilog())
    } else {
        Napier.base(
            object : Antilog() {
                override fun performLog(
                    priority: LogLevel,
                    tag: String?,
                    throwable: Throwable?,
                    message: String?,
                ) {
                    if (priority == LogLevel.INFO || priority == LogLevel.ERROR) {
                        println("${priority.name.uppercase()}: ${tag ?: "null"} - $message")

                        if (throwable !is EOFException) {
                            throwable?.printStackTrace()
                        }
                    }
                }
            }
        )
    }
}

private suspend fun createKord(config: DiscordConfig): Kord {
    return Kord(
        token = config.discordBotApiKey
    ) {
        httpClient = HttpClient(Java) {
            install(ContentNegotiation) {
                json(
                    Json {
                        encodeDefaults = false
                        allowStructuredMapKeys = true
                        ignoreUnknownKeys = true
                        isLenient = true
                    }
                )
            }

            install(HttpTimeout) {
                requestTimeoutMillis = 1L shl 20
                socketTimeoutMillis = requestTimeoutMillis
            }

            install(WebSockets)
        }
    }
}

private fun getConfig(): DiscordConfig {
    val apiKey = System.getenv(ENV_API_DISCORD)
        ?: throw IllegalStateException("Missing env var: $ENV_API_DISCORD")
    val appId = System.getenv(ENV_APP_ID_DISCORD)
        ?: throw IllegalStateException("Missing env var: $ENV_APP_ID_DISCORD")

    Napier.i(tag = TAG) { "Config from env: ${apiKey.maskSecret()}" }

    return DiscordConfig(
        discordBotAppId = appId,
        discordBotApiKey = apiKey,
    )
}

private fun isDebugBuild(): Boolean {
    return System.getenv(BUILD_KEY_ENV) != BUILD_VAL_PROD
}


private const val TAG = "DiscordBot"
