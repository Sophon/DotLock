package io.github.sophon.deadlock.data.mapper

import io.github.sophon.core.arch.DataError
import io.github.sophon.core.arch.WikiError

internal fun DataError.Remote.toDomain(): WikiError {
    val error = when (this) {
        DataError.Remote.TOO_MANY_REQUESTS,
        DataError.Remote.REQUEST_TIMEOUT,
        DataError.Remote.NO_INTERNET,
        DataError.Remote.SERVER_ERROR,
            -> WikiError.NetworkError(this.name)

        DataError.Remote.SERIALIZATION_ERROR -> WikiError.ParseError(this.name)
        DataError.Remote.UNKNOWN -> WikiError.Unknown(this.name)
    }
    return error
}