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
        .map { if (it.isWhitespace() || it == '-') '_' else it }
        .joinToString("")
        .lowercase()
}

fun String.toSnakeCase(): String {
    return replace(Regex("([a-z])([A-Z])")) { "${it.groupValues[1]}_${it.groupValues[2]}" }
        .replace(Regex("([A-Z]+)([A-Z][a-z])")) { "${it.groupValues[1]}_${it.groupValues[2]}" }
        .uppercase()
}

fun getEmptyChar(): String = "\u200b"

fun String.cleanHtml(): String {
    return this
        .decodeHtmlEntities()
        .removeHtmlTags()
        .replace("'''", "") //wiki bolt
        .replace(Regex("\\*\\s*\\n"), "* ")
        .trim()
}

private fun String.decodeHtmlEntities(): String {
    return this
        // Decode &amp; FIRST so double-encoded entities work
        .replace("&amp;", "&")
        // NOW handle numeric entities
        .replace(Regex("&#(\\d+);")) { matchResult ->
            matchResult.groupValues[1].toInt().toChar().toString()
        }
        .replace(Regex("&#x([0-9A-Fa-f]+);")) { matchResult ->
            matchResult.groupValues[1].toInt(16).toChar().toString()
        }
        // Then other named entities
        .replace("&gt;", ">")
        .replace("&lt;", "<")
        .replace("&quot;", "\"")
        .replace("&#039;", "'")
        .replace("&nbsp;", " ")
        .replace("&apos;", "'")
}

private fun String.removeHtmlTags(): String {
    return this
        .replace(Regex("<br\\s*/?>"), "\n")
        .replace(Regex("<[^>]*>"), "")
}

fun String.cleanDescription(): String {
    return cleanHtml()
        .replace(Regex("\\{[^}]*\\}")) { match ->
            match.value
                .substringAfterLast(':')
                .trim('\'', '}')
                .replace(Regex("([a-z])([A-Z])"), "$1 $2")
        }
        .normalizeWhiteSpace()
        .trim()
}
