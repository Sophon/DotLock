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

fun String?.orDash(): String = this?.takeUnless { it.isBlank() } ?: "-"

fun String.truncate(maxLength: Int): String {
    return if (length > maxLength) {
        take(maxLength - 3) + "..."
    } else this
}