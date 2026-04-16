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

fun String.removeTag(): String {
    return if (contains("@")) {
        this
            .substringAfter("@")
            .substringAfter(" ")
    } else this
}

fun String.normalizeWhiteSpace(): String {
    return this.replace(Regex("\\s+"), " ")
}

fun String.extractFirstWord(): String {
    return this
        .trim()
        .substringBefore(' ')
}

fun String.formKey(): String {
    return trim()
        .map { if (it.isWhitespace()) '_' else it }
        .joinToString("")
        .lowercase()
}

fun String.toSnakeCase(): String {
    return replace(Regex("([a-z])([A-Z])")) { "${it.groupValues[1]}_${it.groupValues[2]}" }
        .replace(Regex("([A-Z]+)([A-Z][a-z])")) { "${it.groupValues[1]}_${it.groupValues[2]}" }
        .uppercase()
}

fun getEmptyChar(): String = "\u200b"
