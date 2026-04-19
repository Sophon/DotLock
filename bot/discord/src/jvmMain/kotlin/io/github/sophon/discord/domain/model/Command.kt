package io.github.sophon.discord.domain.model

internal sealed class Command(
    val name: String,
    val description: String,
    val argumentList: List<Argument> = listOf(),
) {
    object Item: Command(
        name = "item",
        description = "Item data",
        argumentList = listOf(
            Argument(
                name = "item",
                description = "item name",
            )
        )
    )

    object Hero: Command(
        name = "hero",
        description = "Hero data",
        argumentList = listOf(
            Argument(
                name = "hero",
                description = "hero name",
            )
        )
    )

    object Ability: Command(
        name = "ability",
        description = "Ability data",
        argumentList = listOf(
            Argument(
                name = "ability",
                description = "ability name",
            )
        )
    )


    data class Argument(
        val name: String,
        val description: String,
        val isRequired: Boolean = true,
    )

    companion object {
        val entries: List<Command> by lazy {
            Command::class.sealedSubclasses
                .mapNotNull { it.objectInstance }
        }

        fun Companion.fromStringOrNull(value: String): Command? {
            return entries.find { it.name.equals(value, ignoreCase = true) }
        }
    }
}