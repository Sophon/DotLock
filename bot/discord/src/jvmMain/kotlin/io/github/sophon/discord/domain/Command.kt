package io.github.sophon.discord.domain

internal sealed class Command(
    val name: String,
    val description: String,
    val argumentList: List<Argument> = listOf(),
) {
    //TODO: ability
    //TODO: hero
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