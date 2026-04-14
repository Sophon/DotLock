package io.github.sophon.deadlock.remote

import io.github.sophon.core.arch.DataError
import io.github.sophon.core.arch.Result
import io.github.sophon.core.arch.map
import io.github.sophon.core.util.formKey

internal class ImageResolver(
    private val source: DeadlockWikiDataSource,
) {
    suspend fun resolveImageUrl(
        names: List<String>,
    ): Result<Map<String, String>, DataError.Remote> {
        val nameToFileName = names.associateWith { "${it.toFileName()}.png" }

        return source.getImageUrl(nameToFileName.values.toList())
            .map { fileNameToUrl ->
                nameToFileName.mapNotNull { (name, fileName) ->
                    val url = fileNameToUrl[fileName] ?: return@mapNotNull null
                    name.formKey() to url
                }.toMap()
            }
    }

    private fun String.toFileName(): String {
        return trim()
            .map { if (it.isWhitespace()) '_' else it }
            .joinToString("")
    }
}
