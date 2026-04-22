package io.github.sophon.deadlock.usecase

import assertk.assertThat
import assertk.assertions.isEqualTo
import io.github.sophon.core.arch.DataError
import io.github.sophon.core.arch.EmptyResult
import io.github.sophon.core.arch.Result
import io.github.sophon.core.arch.WikiError
import io.github.sophon.core.domain.model.Item
import io.github.sophon.deadlock.ItemSource
import io.github.sophon.deadlock.db.ItemDatabase
import io.github.sophon.deadlock.remote.DeadlockWikiDataSource
import io.github.sophon.deadlock.remote.ImageResolver
import io.github.sophon.deadlock.remote.dto.HeroAbilitiesDto
import io.github.sophon.deadlock.remote.dto.HeroDto
import io.github.sophon.deadlock.remote.dto.ItemDto
import kotlinx.coroutines.test.runTest
import org.junit.Test

internal class DownloadItemsUseCaseTest {
    private val db = DatabaseFake()
    private val usecase = DownloadItemsUseCase(
        source = SourceFake(),
        db = db,
        imageResolver = ResolverFake(),
    )

    @Test
    fun `usecase handles upgrade path`() = runTest {
        // given
        val expectedFrom = listOf("Compress Cooldown")
        val expectedTo = listOf("Transcendent Cooldown")

        // when
        usecase.invoke(emptyMap())
        val item = (db.fetchItem("Superior Cooldown") as Result.Success).data

        //then
        assertThat(item.upgradePath.from).isEqualTo(expectedFrom)
        assertThat(item.upgradePath.to).isEqualTo(expectedTo)
    }
    
    @Test
    fun `usecase handles multiple upgrade paths`() = runTest {
        // given
        val expectedFrom = emptyList<String>()
        val expectedTo = listOf(
            "Leech",
            "Infuser",
        )

        // when
        usecase.invoke(emptyMap())
        val item = (db.fetchItem("Spirit Lifesteal") as Result.Success).data

        //then
        assertThat(item.upgradePath.from).isEqualTo(expectedFrom)
        assertThat(item.upgradePath.to).isEqualTo(expectedTo)
    }


    private class SourceFake: DeadlockWikiDataSource {
        override suspend fun downloadDescriptions(): Result<Map<String, String>, DataError.Remote> {
            return Result.Success(emptyMap())
        }

        override suspend fun downloadHeroList(): Result<Map<String, HeroDto>, DataError.Remote> {
            return Result.Success(emptyMap())
        }

        override suspend fun downloadAbilityList(): Result<Map<String, HeroAbilitiesDto>, DataError.Remote> {
            return Result.Success(emptyMap())
        }

        override suspend fun downloadItemList(): Result<Map<String, ItemDto>, DataError.Remote> {
            val map = mapOf(
                "upgrade_magic_tempo" to ItemSource.compressCooldown,
                "upgrade_cooldown_reduction" to ItemSource.superiorCooldown,
                "upgrade_transcendent_cooldown" to ItemSource.transcendentCooldown,
                "upgrade_health_stealing_magic" to ItemSource.spiritLifesteal,
                "upgrade_damage_recycler" to ItemSource.leech,
                "upgrade_infuser" to ItemSource.infuser,
            )

            return Result.Success(map)
        }

        override suspend fun getImageUrl(fileNames: List<String>): Result<Map<String, String>, DataError.Remote> {
            TODO("Not yet implemented")
        }
    }

    private class DatabaseFake: ItemDatabase {
        val db = mutableListOf<Item>()

        override suspend fun insert(itemList: List<Item>): EmptyResult<WikiError> {
            db.addAll(itemList)
            return Result.Success(Unit)
        }

        override suspend fun fetchItem(name: String): Result<Item, WikiError> {
            val item = db.firstOrNull { it.name == name }
            return if (item == null) {
                Result.Error(WikiError.NotFound(""))
            } else {
                Result.Success(item)
            }
        }

        override suspend fun fetchItemList(predicate: (Item) -> Boolean): Result<List<Item>, WikiError> {
            return Result.Success(emptyList())
        }

        override suspend fun clear(): EmptyResult<WikiError> {
            return Result.Success(Unit)
        }
    }

    private class ResolverFake: ImageResolver {
        override suspend fun resolveImageUrl(names: List<String>): Result<Map<String, String>, DataError.Remote> {
            return Result.Success(emptyMap())
        }
    }
}
