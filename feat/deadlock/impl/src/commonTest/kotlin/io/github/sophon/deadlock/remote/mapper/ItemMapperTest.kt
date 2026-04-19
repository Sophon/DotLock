package io.github.sophon.deadlock.remote.mapper

import assertk.assertThat
import assertk.assertions.isEqualTo
import io.github.sophon.core.domain.model.ActivationType
import io.github.sophon.core.domain.model.Bonus
import io.github.sophon.core.domain.model.Item
import io.github.sophon.core.domain.model.Property
import io.github.sophon.core.domain.model.Scale
import io.github.sophon.core.domain.model.ScaledValue
import io.github.sophon.deadlock.remote.dto.ItemDto
import io.github.sophon.deadlock.remote.dto.ItemInfoDto
import io.github.sophon.deadlock.remote.dto.ItemPropDto
import io.github.sophon.deadlock.remote.dto.OtherDto
import io.github.sophon.deadlock.remote.dto.ScaleDto
import kotlinx.serialization.json.JsonPrimitive
import org.junit.Test

internal class ItemMapperTest {

    @Test
    fun `toDomain handles basic bonus structure`() {
        // given
        val item = ItemSource.capacitor
        val expected = listOf(
            Bonus(
                type = ActivationType.INNATE,
                description = null,
                cooldown = null,
                chargeUp = null,
                properties = listOf(
                    Property(
                        key = Property.Key.BONUS_FIRE_RATE,
                        value = ScaledValue(value = 5.0, scale = null),
                        type = "fire_rate",
                    ),
                ),
            ),
            Bonus(
                type = ActivationType.PASSIVE,
                description = "#upgrade_chain_lightning_desc",
                cooldown = 0.25,
                chargeUp = null,
                properties = listOf(
                    Property(
                        key = Property.Key.DAMAGE_PER_CHAIN,
                        value = ScaledValue(
                            value = 43.0,
                            scale = Scale(value = 0.19, type = ScaledValue.ScaleType.SPIRIT),
                        ),
                        type = "tech_damage",
                    ),
                    Property(
                        key = Property.Key.PROC_CHANCE,
                        value = ScaledValue(value = 20.0, scale = null),
                        type = "",
                    ),
                    Property(
                        key = Property.Key.CHAIN_COUNT,
                        value = ScaledValue(value = 6.0, scale = null),
                        type = "",
                    ),
                    Property(
                        key = Property.Key.CHAIN_RADIUS,
                        value = ScaledValue(value = 0.0, scale = null),
                        type = "distance",
                    ),
                ),
            ),
            Bonus(
                type = ActivationType.ACTIVE,
                description = "#upgrade_capacitor_desc",
                cooldown = 40.0,
                chargeUp = null,
                properties = listOf(
                    Property(
                        key = Property.Key.DAMAGE,
                        value = ScaledValue(value = 100.0, scale = null),
                        type = "tech_damage",
                    ),
                    Property(
                        key = Property.Key.MAX_SLOW_PERCENT,
                        value = ScaledValue(value = 75.0, scale = null),
                        type = "slow",
                    ),
                    Property(
                        key = Property.Key.SLOW_DURATION,
                        value = ScaledValue(value = 3.0, scale = null),
                        type = "duration",
                    ),
                ),
            ),
        )

        // when
        val result = item.toDomain("", emptyMap())

        //then
        assertThat(result.bonusList).isEqualTo(expected)
    }
    
    @Test
    fun `toDomain handles URL for multi name move`() {
        // given
        val item = ItemSource.mercurialMagnum
        val expected = "https://deadlock.wiki/Mercurial_Magnum"
        
        // when
        val result = item.toDomain("", emptyMap())

        //then
        assertThat(result.url.wiki).isEqualTo(expected)
    }

    @Test
    fun `toDomain handles shop info`() {
        // given
        val item = ItemSource.mercurialMagnum
        val expectedCost = 6400
        val expectedTier = 4
        val expectedSlot = Item.Slot.TECH
        val expectedComponents = listOf("upgrade_quick_silver")
        val expectedShopFilters = listOf(
            Item.ShopFilter.MAGIC_DAMAGE,
            Item.ShopFilter.FIRE_RATE,
        )

        // when
        val result = item.toDomain("", emptyMap())

        //then
        assertThat(result.cost).isEqualTo(expectedCost)
        assertThat(result.tier).isEqualTo(expectedTier)
        assertThat(result.slot).isEqualTo(expectedSlot)
        assertThat(result.componentList).isEqualTo(expectedComponents)
        assertThat(result.shopFilterList).isEqualTo(expectedShopFilters)
    }

    @Test
    fun `toDomain formats description`() {
        // given
        val item = ItemSource.mercurialMagnum
        val expected = "Your imbued ability charges up over time with Bonus Spirit Damage," +
                " Bonus Fire Rate, and reloads bullets on use. Until your next reload, " +
                "your bullets deal Bonus Spirit Damage based on your Spirit Power."

        // when
        val result = item.toDomain("", emptyMap())

        //then
        assertThat(result.description).isEqualTo(expected)
    }
}


private object ItemSource {
    val capacitor = ItemDto(
        key = "upgrade_capacitor",
        name = "Capacitor",
        descriptionKey = "Launch a projectile that deals <span class=\"highlight\">{g:citadel_inline_attribute:'SpiritIcon'}damage</span>, applies a strong slow that recovers over time, <span class=\"highlight\">prevents Stamina usage</span> and <span class=\"highlight\">Silences</span> their <span class=\"highlight\">movement-based items and abilities</span>.",
        cost = 6400,
        tier = 4,
        slot = "Weapon",
        activation = "InstantCast",
        components = listOf("upgrade_chain_lightning"),
        targetTypes = listOf("HeroEnemy", "CreepEnemy", "BossEnemy", "MinionEnemy"),
        shopFilters = listOf("MagicDamage", "FireRate"),
        isDisabled = false,
        streetBrawl = false,
        info1 = ItemInfoDto(
            type = "Innate",
            descKey = null,
            cooldown = null,
            chargeUp = null,
            main = listOf(
                ItemPropDto(
                    key = "BonusFireRate",
                    value = JsonPrimitive(5),
                    type = "fire_rate",
                ),
            ),
            alt = emptyList(),
        ),
        info2 = ItemInfoDto(
            type = "Passive",
            descKey = "#upgrade_chain_lightning_desc",
            cooldown = 0.25,
            chargeUp = null,
            main = listOf(
                ItemPropDto(
                    key = "DamagePerChain",
                    value = JsonPrimitive(43.0),
                    scaleDto = ScaleDto(value = 0.19, type = "spirit"),
                    type = "tech_damage",
                ),
                ItemPropDto(
                    key = "ProcChance",
                    value = JsonPrimitive(20),
                ),
            ),
            alt = listOf(
                ItemPropDto(
                    key = "ChainCount",
                    value = JsonPrimitive(6),
                ),
                ItemPropDto(
                    key = "ChainRadius",
                    value = JsonPrimitive("10m"),
                    type = "distance",
                ),
            ),
        ),
        info3 = ItemInfoDto(
            type = "Active",
            descKey = "#upgrade_capacitor_desc",
            cooldown = 40.0,
            chargeUp = null,
            main = listOf(
                ItemPropDto(
                    key = "Damage",
                    value = JsonPrimitive(100),
                    type = "tech_damage",
                ),
                ItemPropDto(
                    key = "MaxSlowPercent",
                    value = JsonPrimitive(75),
                    type = "slow",
                ),
            ),
            alt = listOf(
                ItemPropDto(
                    key = "SlowDuration",
                    value = JsonPrimitive(3),
                    type = "duration",
                ),
            ),
        ),
        info4 = null,
        upgrades = mapOf(
            "ProcChance" to JsonPrimitive(5),
            "BonusFireRate" to JsonPrimitive(15),
            "DamagePerChain" to JsonPrimitive(25),
            "AbilityCooldown" to JsonPrimitive(-32),
            "Damage" to JsonPrimitive(25),
        ),
        other = mapOf(
            "AbilityUnitTargetLimit" to OtherDto(
                key = "AbilityUnitTargetLimit",
                value = JsonPrimitive(1),
            ),
            "AbilityCastDelay" to OtherDto(
                key = "AbilityCastDelay",
                value = JsonPrimitive(0.2),
                type = "cast",
            ),
            "AbilityCooldownBetweenCharge" to OtherDto(
                key = "AbilityCooldownBetweenCharge",
                value = JsonPrimitive(-1.0),
                type = "charge_cooldown",
            ),
            "ChannelMoveSpeed" to OtherDto(
                key = "ChannelMoveSpeed",
                value = JsonPrimitive(-1),
                type = "move_speed",
            ),
            "BonusPerChain" to OtherDto(
                key = "BonusPerChain",
                value = JsonPrimitive(43.0),
                type = "tech_damage",
            ),
            "ChainTickRate" to OtherDto(
                key = "ChainTickRate",
                value = JsonPrimitive(0.4),
            ),
        ),
    )
    val mercurialMagnum = ItemDto(
        key = "upgrade_ethereal_bullets",
        name = "Mercurial Magnum",
        descriptionKey = "Your imbued ability charges up over time with {g:citadel_inline_attribute:'BonusSpiritDamage'}, {g:citadel_inline_attribute:'BonusFireRate'}, and <span class=\"highlight\">reloads bullets</span> on use. Until your next reload, your <span class=\"highlight\">bullets deal {g:citadel_inline_attribute:'BonusSpiritDamage'}</span> based on your Spirit Power.",
        cost = 6400,
        tier = 4,
        slot = "Tech",
        activation = "Passive",
        components = listOf("upgrade_quick_silver"),
        targetTypes = listOf("AllEnemy"),
        shopFilters = listOf("MagicDamage", "FireRate"),
        isDisabled = false,
        streetBrawl = false,
        info1 = ItemInfoDto(
            type = "Innate",
            descKey = null,
            cooldown = null,
            chargeUp = null,
            main = emptyList(),
            alt = listOf(
                ItemPropDto(
                    key = "BonusClipSizePercent",
                    value = JsonPrimitive(20),
                ),
                ItemPropDto(
                    key = "TechPower",
                    value = JsonPrimitive(7),
                ),
            ),
        ),
        info2 = ItemInfoDto(
            type = "Passive",
            descKey = "#upgrade_ethereal_bullets_desc",
            cooldown = null,
            chargeUp = 14.0,
            main = listOf(
                ItemPropDto(
                    key = "BulletsBonusMagicDamage",
                    value = JsonPrimitive(25.0),
                    scaleDto = ScaleDto(value = 0.465, type = "spirit"),
                    type = "tech_damage",
                ),
                ItemPropDto(
                    key = "Damage",
                    value = JsonPrimitive(60.0),
                    scaleDto = ScaleDto(value = 0.16, type = "spirit"),
                    type = "tech_damage",
                ),
                ItemPropDto(
                    key = "BonusFireRate",
                    value = JsonPrimitive(22),
                    type = "fire_rate",
                ),
            ),
            alt = listOf(
                ItemPropDto(
                    key = "AmmoReloadPercent",
                    value = JsonPrimitive(100),
                ),
            ),
        ),
        info3 = null,
        info4 = null,
        upgrades = mapOf(
            "BonusFireRate" to JsonPrimitive(20),
            "BonusClipSizePercent" to JsonPrimitive(60),
            "Damage" to JsonPrimitive(120),
            "BulletsBonusMagicDamage" to JsonPrimitive(20),
            "TechPower" to JsonPrimitive(15),
        ),
        other = mapOf(
            "AbilityCooldown" to OtherDto(
                key = "AbilityCooldown",
                value = JsonPrimitive(15),
                type = "cooldown",
            ),
            "AbilityUnitTargetLimit" to OtherDto(
                key = "AbilityUnitTargetLimit",
                value = JsonPrimitive(1),
            ),
            "AbilityCooldownBetweenCharge" to OtherDto(
                key = "AbilityCooldownBetweenCharge",
                value = JsonPrimitive(-1.0),
                type = "charge_cooldown",
            ),
            "ChannelMoveSpeed" to OtherDto(
                key = "ChannelMoveSpeed",
                value = JsonPrimitive(-1),
                type = "move_speed",
            ),
            "BuffDuration" to OtherDto(
                key = "BuffDuration",
                value = JsonPrimitive(12),
            ),
        ),
    )
}
