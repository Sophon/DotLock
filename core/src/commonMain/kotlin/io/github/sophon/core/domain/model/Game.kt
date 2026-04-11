package io.github.sophon.core.domain.model

enum class Game(
    val id: String,
    val iconUrl: String,
    val wikiUrl: String,
) {
    Deadlock(
        id = "Deadlock",
        iconUrl = "https://deadlock.wiki/images/6/6b/Deadlock_Logo.png",
        wikiUrl = "https://deadlock.wiki/",
    )
}