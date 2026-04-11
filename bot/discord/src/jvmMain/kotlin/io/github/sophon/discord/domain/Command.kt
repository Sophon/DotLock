package io.github.sophon.discord.domain

internal sealed class Command(
    val name: String,
    val description: String,
    val argumentList: List<Argument> = listOf(),
) {
    //TODO: ability
    //TODO: hero
    //TODO: item


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
    }
}