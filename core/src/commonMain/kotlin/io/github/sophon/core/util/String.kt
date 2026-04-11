package io.github.sophon.core.util

fun String.maskSecret(): String {
    return when {
        isEmpty() -> ""
        length == 1 -> "*"
        (length <= 8) -> {
            "${first()}${"*".repeat(length - 2)}${last()}"
        }
        else -> {
            "${take(4)}${"*".repeat(length - 8)}${takeLast(4)}"
        }
    }
}