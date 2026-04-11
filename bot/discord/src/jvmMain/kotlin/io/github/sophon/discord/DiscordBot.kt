package io.github.sophon.discord

import dev.kord.common.entity.Permission
import dev.kord.common.entity.Permissions
import dev.kord.common.entity.Snowflake
import dev.kord.core.Kord
import dev.kord.core.event.gateway.DisconnectEvent
import dev.kord.core.on
import dev.kord.rest.builder.interaction.string
import io.github.aakira.napier.Napier
import io.github.sophon.discord.domain.DiscordRegisteredFeature
import io.github.sophon.discord.domain.adminCommands
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collect

interface DiscordBot {
    suspend fun startSession()
}


internal class DiscordBotImpl(
    private val kord: Kord,
    private val coroutineScope: CoroutineScope,

//    private val featureList: List<DiscordRegisteredFeature> = listOf(), //TODO: refactor
//    private val adminConfig: Config.AdminConfig,
): DiscordBot {
    override suspend fun startSession() {
        Napier.i(tag = TAG) { "🚀 Bot starting..." }

        startTracking()
        startKord()

        Napier.e(tag = TAG) { "❌ Bot session ended (this shouldn't happen)" }
    }


    private fun startTracking() {
        //TODO()
    }

    private suspend fun startKord() {
//        cleanOldGuildCommands(kord)
//        createGlobalCommands()
        createAdminCommands()
        createCommandsForTestServer()

        monitorGatewayHealth()

        //‼️ THIS SUSPENDS UNTIL LOGGED OUT
        try {
            kord.login {
//                presence {
//                    playing("/FD | /HELP | /FEEDBACK")
//                }
            }
        } catch (e: Exception) {
            Napier.e(tag = TAG) { "💥 Login failed: ${e.message}" }
            e.printStackTrace()
            throw e
        }

        Napier.e(tag = TAG) { "⚠️ Login ended (bot disconnected)" }
    }

    private suspend fun cleanOldGuildCommands(kord: Kord) {
//        try {
//            val testGuildSnowFlake = Snowflake(adminConfig.adminServerId)
//            kord.getGuildApplicationCommands(testGuildSnowFlake).collect { command ->
//                try {
//                    command.delete()
//                } catch (e: Exception) {
//                    Napier.e(tag = TAG) { "Failed to delete command ${command.name}: ${e.message}" }
//                }
//            }
//        } catch(e: Exception) {
//            Napier.e(tag = TAG, throwable = e) { "Failed to delete old commands" }
//        }
    }

    private suspend fun createGlobalCommands() {
//        try {
//            kord.createGlobalApplicationCommands {
//                featureList
//                    .flatMap { feature -> feature.otherCommands + listOfNotNull(feature.defaultCommand) }
//                    .distinctBy { it.name.lowercase() }
//                    .filter { supportedCommand ->
//                        adminCommands.contains(supportedCommand).not()
//                    }
//                    .forEach { supportedCommand ->
//                        input(
//                            name = supportedCommand.name.lowercase(),
//                            description = supportedCommand.description
//                        ) {
//                            supportedCommand.argumentList.forEach { argument ->
//                                string(name = argument.name, description = argument.description) {
//                                    required = argument.isRequired
//                                }
//                            }
//                        }
//                    }
//            }.collect()
//        } catch (e: Exception) {
//            Napier.e(tag = TAG) { "Failed to create global commands: ${e.message}" }
//        }
    }

    private suspend fun createCommandsForTestServer() {
//        val testGuildSnowFlake = Snowflake(adminConfig.adminServerId)
//        kord.createGuildApplicationCommands(testGuildSnowFlake) {
//            featureList
//                .flatMap { feature -> feature.otherCommands + listOfNotNull(feature.defaultCommand) }
//                .distinctBy { it.name.lowercase() }
//                .forEach { supportedCommand ->
//                    input(
//                        name = supportedCommand.name.lowercase(),
//                        description = supportedCommand.description
//                    ) {
//                        if (adminCommands.contains(supportedCommand)) {
//                            defaultMemberPermissions = Permissions(Permission.Administrator)
//                        }
//
//                        supportedCommand.argumentList.forEach { argument ->
//                            string(name = argument.name, description = argument.description) {
//                                required = argument.isRequired
//                            }
//                        }
//                    }
//                }
//        }.collect()
    }

    private suspend fun createAdminCommands() {
//        try {
//            val adminGuildSnowFlake = Snowflake(adminConfig.adminServerId)
//            kord.createGuildApplicationCommands(adminGuildSnowFlake) {
//                adminCommands.forEach { command ->
//                    input(
//                        name = command.name.lowercase(),
//                        description = command.description
//                    ) {
//                        defaultMemberPermissions = Permissions(Permission.Administrator)
//
//                        command.argumentList.forEach { argument ->
//                            string(name = argument.name, description = argument.description) {
//                                required = argument.isRequired
//                            }
//                        }
//                    }
//                }
//            }.collect()
//        } catch (e: Exception) {
//            Napier.e(tag = TAG) { "Failed to create admin commands: ${e.message}" }
//        }
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


    private companion object {
        const val TAG = "DiscordBot"
    }
}
