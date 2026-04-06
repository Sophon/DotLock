package io.github.sophon.core.util

/**
 * Generic helper to map PascalCase strings to UPPER_SNAKE_CASE Enums.
 */
inline fun <reified T : Enum<T>> String?.toEnumOrDefault(default: T): T {
    if (this == null) return default

    val normalized = this
        .replace("([a-z])([A-Z])".toRegex(), "$1_$2")
        .uppercase()

    return try {
        enumValueOf<T>(normalized)
    } catch (e: IllegalArgumentException) {
        default
    }
}
