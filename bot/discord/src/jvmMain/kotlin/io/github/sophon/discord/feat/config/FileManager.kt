package io.github.sophon.discord.feat.config

import io.github.sophon.core.arch.EmptyResult
import io.github.sophon.core.arch.Result
import io.github.sophon.discord.domain.model.BotError
import java.io.File

internal interface FileManager {
    fun read(path: String): Result<String, BotError>
    fun write(path: String, content: String): EmptyResult<BotError>
    fun exists(path: String): Boolean
    fun create(path: String): EmptyResult<BotError>
}


internal class FileManagerImpl(): FileManager {
    override fun read(path: String): Result<String, BotError> {
        return try {
            Result.Success(File(path).readText())
        } catch (e: Exception) {
            Result.Error(BotError.FileError(path))
        }
    }

    override fun write(path: String, content: String): EmptyResult<BotError> {
        return try {
            File(path).writeText(content)
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(BotError.FileError(path))
        }
    }

    override fun exists(path: String): Boolean {
        return File(path).exists()
    }

    override fun create(path: String): EmptyResult<BotError> {
        return try {
            File(path).createNewFile()
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(BotError.FileError(path))
        }
    }
}