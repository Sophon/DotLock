package io.github.sophon.deadlock.remote.mapper

import assertk.assertThat
import assertk.assertions.isEqualTo
import io.github.sophon.core.domain.model.ActivationType
import io.github.sophon.core.domain.model.Bonus
import io.github.sophon.core.domain.model.Effect
import io.github.sophon.core.domain.model.Scale
import io.github.sophon.core.domain.model.ScaledValue
import io.github.sophon.deadlock.ItemSource
import org.junit.Test

internal class ItemMapperTest {

    @Test
    fun `toDomain handles basic bonus structure`() {
        // given
        val item = ItemSource.capacitor
        val expected = setOf(
            Bonus(
                type = ActivationType.INNATE,
                description = null,
                cooldown = null,
                chargeUp = null,
                effectList = listOf(
                    Effect(
                        key = "BONUS_FIRE_RATE",
                        value = ScaledValue(value = 5.0, scale = null),
                        type = "fire_rate",
                    ),
                ),
            ),
            Bonus(
                type = ActivationType.PASSIVE,
                description = null,
                cooldown = 0.25,
                chargeUp = null,
                effectList = listOf(
                    Effect(
                        key = "DAMAGE_PER_CHAIN",
                        value = ScaledValue(
                            value = 43.0,
                            scale = Scale(value = 0.19, type = ScaledValue.ScaleType.SPIRIT),
                        ),
                        type = "tech_damage",
                    ),
                    Effect(
                        key = "PROC_CHANCE",
                        value = ScaledValue(value = 20.0, scale = null),
                        type = "",
                    ),
                    Effect(
                        key = "CHAIN_COUNT",
                        value = ScaledValue(value = 6.0, scale = null),
                        type = "",
                    ),
                    Effect(
                        key = "CHAIN_RADIUS",
                        value = ScaledValue(value = 0.0, scale = null),
                        type = "distance",
                    ),
                ),
            ),
            Bonus(
                type = ActivationType.ACTIVE,
                description = null,
                cooldown = 40.0,
                chargeUp = null,
                effectList = listOf(
                    Effect(
                        key = "DAMAGE",
                        value = ScaledValue(value = 100.0, scale = null),
                        type = "tech_damage",
                    ),
                    Effect(
                        key = "MAX_SLOW_PERCENT",
                        value = ScaledValue(value = 75.0, scale = null),
                        type = "slow",
                    ),
                    Effect(
                        key = "SLOW_DURATION",
                        value = ScaledValue(value = 3.0, scale = null),
                        type = "duration",
                    ),
                ),
            ),
        )

        // when
        val result = item.toDomain("", emptyMap(),emptyMap())

        //then
        assertThat(result.bonusSet).isEqualTo(expected)
    }
    
    @Test
    fun `toDomain handles URL for multi name move`() {
        // given
        val item = ItemSource.mercurialMagnum
        val expected = "https://deadlock.wiki/Mercurial_Magnum"
        
        // when
        val result = item.toDomain("", emptyMap(), emptyMap())

        //then
        assertThat(result.url.wiki).isEqualTo(expected)
    }

    @Test
    fun `toDomain formats description`() {
        // given
        val item = ItemSource.mercurialMagnum
        val expected = "Your imbued ability charges up over time with Bonus Spirit Damage," +
                " Bonus Fire Rate, and reloads bullets on use. Until your next reload, " +
                "your bullets deal Bonus Spirit Damage based on your Spirit Power."

        // when
        val result = item.toDomain("", emptyMap(), emptyMap())

        //then
        assertThat(result.description).isEqualTo(expected)
    }
}

