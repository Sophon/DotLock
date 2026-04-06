package io.github.sophon.core.arch

sealed class WikiError(open val error: String) : Error {
    data class NetworkError(override val error: String) : WikiError(error)
    data class ParseError(override val error: String) : WikiError(error)
    data class NotFound(override val error: String) : WikiError(error)

    data class Unknown(override val error: String) : WikiError(error)
}
