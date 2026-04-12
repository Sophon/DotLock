package io.github.sophon.core.util

import io.github.sophon.core.domain.model.Game

fun String.getGame(): Game? {
    return Game.entries.find { it.id == this }
}