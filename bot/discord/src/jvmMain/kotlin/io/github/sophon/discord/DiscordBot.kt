package io.github.sophon.discord

import dev.kord.common.entity.Permission
import dev.kord.common.entity.Permissions
import dev.kord.common.entity.Snowflake
import dev.kord.core.Kord
import dev.kord.core.event.gateway.DisconnectEvent
import dev.kord.core.event.interaction.ButtonInteractionCreateEvent
import dev.kord.core.event.interaction.GuildChatInputCommandInteractionCreateEvent
import dev.kord.core.event.message.MessageCreateEvent
import dev.kord.core.on
import dev.kord.rest.builder.interaction.string
import io.github.aakira.napier.Napier
import io.github.sophon.core.arch.onError
import io.github.sophon.discord.feat.config.BotConfig
import io.github.sophon.discord.domain.model.BotOutput
import io.github.sophon.discord.domain.model.Source
import io.github.sophon.discord.domain.model.adminCommands
import io.github.sophon.discord.feat.DiscordRegisteredFeature
import io.github.sophon.discord.feat.emoji.Emojifier
import io.github.sophon.discord.usecase.HandleButtonInteractionUseCase
import io.github.sophon.discord.usecase.ResultToEmbedUseCase
import io.github.sophon.discord.usecase.RouteCommandToFeatureUseCase
import io.github.sophon.discord.util.safeRestCall
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope

interface DiscordBot {
    suspend fun startSession()
}


internal class DiscordBotImpl(
    private val kord: Kord,
    private val coroutineScope: CoroutineScope,

    private val featureList: List<DiscordRegisteredFeature>,
    private val emojifier: Emojifier,
    private val adminConfig: BotConfig.AdminConfig,

    private val routeCommandToFeatureUseCase: RouteCommandToFeatureUseCase,
    private val resultToEmbedUseCase: ResultToEmbedUseCase,
    private val handleButtonInteractionUseCase: HandleButtonInteractionUseCase,
): DiscordBot {
    private val editableEmbedMap = mutableMapOf<String, BotOutput>()

    override suspend fun startSession() {
        Napier.i(tag = TAG) { "🚀 Bot starting..." }

        startFeatures()
        startTracking()
        startKord()

        Napier.e(tag = TAG) { "❌ Bot session ended (this shouldn't happen)" }
    }


    private suspend fun startFeatures() {
        supervisorScope {
            featureList.forEach { feature ->
                launch {
                    runCatching { feature.start() }
                        .onFailure {
                            Napier.e(tag = TAG) { "Failed to load ${feature.featureInfo.name}: $it" }
                        }
                }
            }
        }
        emojifier.load()
    }

    private fun startTracking() {
        //TODO()
    }

    private suspend fun startKord() {
        cleanOldGuildCommands(kord)
        createGlobalCommands()
//        createAdminCommands()
        createCommandsForTestServer()

        monitorGatewayHealth()

        observeBotInteractions()

        //‼️ THIS SUSPENDS UNTIL LOGGED OUT
        try {
            kord.login {
                presence {
                    playing("/ability | /hero | /item")
                }
            }
        } catch (e: Exception) {
            Napier.e(tag = TAG) { "💥 Login failed: ${e.message}" }
            e.printStackTrace()
            throw e
        }

        Napier.e(tag = TAG) { "⚠️ Login ended (bot disconnected)" }
    }

    private suspend fun cleanOldGuildCommands(kord: Kord) {
        try {
            val testGuildSnowFlake = Snowflake(adminConfig.adminServerId)
            kord.getGuildApplicationCommands(testGuildSnowFlake).collect { command ->
                try {
                    command.delete()
                } catch (e: Exception) {
                    Napier.e(tag = TAG) { "Failed to delete command ${command.name}: ${e.message}" }
                }
            }
        } catch(e: Exception) {
            Napier.e(tag = TAG, throwable = e) { "Failed to delete old commands" }
        }
    }

    private suspend fun createGlobalCommands() {
        try {
            kord.createGlobalApplicationCommands {
                featureList
                    .flatMap { feature -> feature.supportedCommands }
                    .distinctBy { it.name.lowercase() }
                    .filter { supportedCommand ->
                        adminCommands.contains(supportedCommand).not()
                    }
                    .forEach { supportedCommand ->
                        input(
                            name = supportedCommand.name.lowercase(),
                            description = supportedCommand.description
                        ) {
                            supportedCommand.argumentList.forEach { argument ->
                                string(name = argument.name, description = argument.description) {
                                    required = argument.isRequired
                                }
                            }
                        }
                    }
            }.collect()
        } catch (e: Exception) {
            Napier.e(tag = TAG) { "Failed to create global commands: ${e.message}" }
        }
    }

    private suspend fun createCommandsForTestServer() {
        val testGuildSnowFlake = Snowflake(adminConfig.adminServerId)
        kord.createGuildApplicationCommands(testGuildSnowFlake) {
            featureList
                .flatMap { feature -> feature.supportedCommands }
                .distinctBy { it.name.lowercase() }
                .forEach { supportedCommand ->
                    input(
                        name = supportedCommand.name.lowercase(),
                        description = supportedCommand.description
                    ) {
                        if (adminCommands.contains(supportedCommand)) {
                            defaultMemberPermissions = Permissions(Permission.Administrator)
                        }

                        supportedCommand.argumentList.forEach { argument ->
                            string(name = argument.name, description = argument.description) {
                                required = argument.isRequired
                            }
                        }
                    }
                }
        }.collect()
    }

    private suspend fun createAdminCommands() {
        try {
            val adminGuildSnowFlake = Snowflake(adminConfig.adminServerId)
            kord.createGuildApplicationCommands(adminGuildSnowFlake) {
                adminCommands.forEach { command ->
                    input(
                        name = command.name.lowercase(),
                        description = command.description
                    ) {
                        defaultMemberPermissions = Permissions(Permission.Administrator)

                        command.argumentList.forEach { argument ->
                            string(name = argument.name, description = argument.description) {
                                required = argument.isRequired
                            }
                        }
                    }
                }
            }.collect()
        } catch (e: Exception) {
            Napier.e(tag = TAG) { "Failed to create admin commands: ${e.message}" }
        }
    }

    private fun monitorGatewayHealth() {
        kord.on<DisconnectEvent.RetryLimitReachedEvent> {
            Napier.e(tag = TAG) { "Gateway failed to recover on shard $shard - retry limit reached" }
        }

        kord.on<DisconnectEvent.DiscordCloseEvent> {
            if (recoverable.not()) {
                Napier.e(tag = TAG) {
                    "Non-recoverable disconnect on shard $shard: code=${closeCode.code} ($closeCode)"
                }
            } else {
                Napier.d(tag = TAG) {
                    "Gateway disconnect on shard $shard: code=${closeCode.code}, will reconnect"
                }
            }
        }

        kord.on<dev.kord.core.event.gateway.ResumedEvent> {
            Napier.i(tag = TAG) { "Gateway resumed successfully" }
        }
    }

    private fun observeBotInteractions() {
        kord.on<GuildChatInputCommandInteractionCreateEvent> {
            safeRestCall(TAG) { handleCommand() }
        }
        kord.on<MessageCreateEvent> {
            // ignoring other bots, even ourselves
            if (message.author?.isBot != false) return@on

            // ignoring if someone replies with tag
            val botId = kord.selfId
            val botMention = "<@$botId>"
            val botNicknameMention = "<@!$botId>"
            if (botMention !in message.content && botNicknameMention !in message.content) {
                return@on
            }

            safeRestCall(TAG) { handleMessage() }
        }
        kord.on<ButtonInteractionCreateEvent> {
            handleButtonInteractionUseCase.invoke(interaction, editableEmbedMap, coroutineScope)
                .onError { error ->
                    Napier.e(tag = TAG) { "${interaction.data.guildId} → Button interaction: $error" }
                }
        }
    }

    private suspend fun GuildChatInputCommandInteractionCreateEvent.handleCommand() {
        val command = interaction.command.rootName
            .lowercase()
        val query = interaction.command.strings.values
            .joinToString(" ")
        val source = Source(
            username = interaction.user.username,
            id = interaction.user.data.id.toString(),
            channelId = interaction.channelId.toString(),
            serverName = interaction.getGuildOrNull()?.name.orEmpty(),
        )

        val result = routeCommandToFeatureUseCase.invoke(
            command = command,
            query = query,
            source = source,
        ).onError { error ->
                Napier.e(tag = TAG) { error.toString() }
            }

        with (resultToEmbedUseCase) {
            invoke(source, result, coroutineScope, editableEmbedMap)
        }
    }

    private suspend fun MessageCreateEvent.handleMessage() {
        if (kord.selfId !in message.mentionedUserIds) return

        val source = Source(
            username = message.author?.username.orEmpty(),
            id = message.author?.id.toString(),
            channelId = message.channelId.toString(),
            serverName = message.getGuildOrNull()?.name.orEmpty(),
        )

        val result = routeCommandToFeatureUseCase.invoke(
            message = message.content,
            source = source,
        ).onError { error ->
                Napier.e(tag = TAG) { error.toString() }
            }

        with (resultToEmbedUseCase) {
            invoke(source, result, coroutineScope, editableEmbedMap)
        }
    }


    private companion object {
        const val TAG = "DiscordBot"
    }
}
