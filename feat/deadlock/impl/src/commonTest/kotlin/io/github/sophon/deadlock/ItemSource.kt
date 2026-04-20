package io.github.sophon.deadlock

import io.github.sophon.deadlock.remote.dto.ItemDto
import io.github.sophon.deadlock.remote.dto.ItemInfoDto
import io.github.sophon.deadlock.remote.dto.ItemPropDto
import io.github.sophon.deadlock.remote.dto.OtherDto
import io.github.sophon.deadlock.remote.dto.ScaleDto
import kotlinx.serialization.json.JsonPrimitive

internal object ItemSource {
    val capacitor = ItemDto(
        key = "upgrade_capacitor",
        name = "Capacitor",
        description = "Launch a projectile that deals <span class=\"highlight\">{g:citadel_inline_attribute:'SpiritIcon'}damage</span>, applies a strong slow that recovers over time, <span class=\"highlight\">prevents Stamina usage</span> and <span class=\"highlight\">Silences</span> their <span class=\"highlight\">movement-based items and abilities</span>.",
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
        description = "Your imbued ability charges up over time with {g:citadel_inline_attribute:'BonusSpiritDamage'}, {g:citadel_inline_attribute:'BonusFireRate'}, and <span class=\"highlight\">reloads bullets</span> on use. Until your next reload, your <span class=\"highlight\">bullets deal {g:citadel_inline_attribute:'BonusSpiritDamage'}</span> based on your Spirit Power.",
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
    val compressCooldown = ItemDto(
        key = "upgrade_magic_tempo",
        name = "Compress Cooldown",
        description = "Imbue an ability to reduce its <span class=\"highlight\">Cooldown</span>.",
        cost = 1600,
        tier = 2,
        slot = "Tech",
        activation = "Passive",
        components = null,
        targetTypes = null,
        shopFilters = listOf("Healing"),
        isDisabled = false,
        streetBrawl = false,
        info1 = ItemInfoDto(
            type = "Passive",
            descKey = "#upgrade_magic_tempo_desc",
            main = listOf(
                ItemPropDto(
                    key = "CooldownReduction",
                    value = JsonPrimitive(18),
                    type = "cooldown",
                ),
            ),
        ),
        upgrades = mapOf("CooldownReduction" to JsonPrimitive(10)),
        other = mapOf(
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
        ),
    )
    val superiorCooldown = ItemDto(
        key = "upgrade_cooldown_reduction",
        name = "Superior Cooldown",
        description = "Reduces the <span class=\"highlight\">Cooldown</span> of your abilities.",
        cost = 3200,
        tier = 3,
        slot = "Tech",
        activation = "Passive",
        components = listOf("upgrade_magic_tempo"),
        targetTypes = null,
        shopFilters = listOf("Healing"),
        isDisabled = false,
        streetBrawl = false,
        info1 = ItemInfoDto(
            type = "Innate",
            alt = listOf(
                ItemPropDto(
                    key = "OutOfCombatHealthRegen",
                    value = JsonPrimitive(4),
                    type = "healing",
                ),
            ),
        ),
        info2 = ItemInfoDto(
            type = "Passive",
            descKey = "#upgrade_cooldown_reduction_desc",
            main = listOf(
                ItemPropDto(
                    key = "CooldownReduction",
                    value = JsonPrimitive(20),
                    type = "cooldown",
                ),
            ),
        ),
        upgrades = mapOf(
            "CooldownReduction" to JsonPrimitive(10),
            "OutOfCombatHealthRegen" to JsonPrimitive(6),
        ),
        other = mapOf(
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
        ),
    )
    val transcendentCooldown = ItemDto(
        key = "upgrade_transcendent_cooldown",
        name = "Transcendent Cooldown",
        description = "Reduces the <span class=\"highlight\">Cooldown</span> of your abilities and items.",
        cost = 6400,
        tier = 4,
        slot = "Tech",
        activation = "Passive",
        components = listOf("upgrade_cooldown_reduction"),
        targetTypes = null,
        shopFilters = listOf("Healing"),
        isDisabled = false,
        streetBrawl = false,
        info1 = ItemInfoDto(
            type = "Innate",
            alt = listOf(
                ItemPropDto(
                    key = "OutOfCombatHealthRegen",
                    value = JsonPrimitive(4),
                    type = "healing",
                ),
            ),
        ),
        info2 = ItemInfoDto(
            type = "Passive",
            descKey = "#upgrade_transcendent_cooldown_desc",
            main = listOf(
                ItemPropDto(
                    key = "CooldownReduction",
                    value = JsonPrimitive(25),
                    type = "cooldown",
                ),
                ItemPropDto(
                    key = "ItemCooldownReduction",
                    value = JsonPrimitive(25),
                    type = "cooldown",
                ),
            ),
        ),
        upgrades = mapOf(
            "ItemCooldownReduction" to JsonPrimitive(10),
            "OutOfCombatHealthRegen" to JsonPrimitive(10),
            "CooldownReduction" to JsonPrimitive(15),
        ),
        other = mapOf(
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
        ),
    )
    val spiritLifesteal = ItemDto(
        key = "upgrade_health_stealing_magic",
        name = "Spirit Lifesteal",
        description = null,
        cost = 1600,
        tier = 2,
        slot = "Armor",
        activation = "Passive",
        components = null,
        targetTypes = null,
        shopFilters = listOf("MagicDamage", "Healing", "Durability"),
        isDisabled = false,
        streetBrawl = false,
        info1 = ItemInfoDto(
            type = "Innate",
            main = listOf(
                ItemPropDto(
                    key = "AbilityLifestealPercentHero",
                    value = JsonPrimitive(16),
                    type = "healing",
                ),
            ),
            alt = listOf(
                ItemPropDto(
                    key = "BonusHealth",
                    value = JsonPrimitive(70),
                    type = "health",
                ),
                ItemPropDto(
                    key = "TechPower",
                    value = JsonPrimitive(6),
                    type = "tech_damage",
                ),
            ),
        ),
        upgrades = mapOf(
            "AbilityLifestealPercentHero" to JsonPrimitive(14),
            "BonusHealth" to JsonPrimitive(80),
            "TechPower" to JsonPrimitive(9),
        ),
        other = mapOf(
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
            "NonHeroAbilityLifestealTooltipOnly" to OtherDto(
                key = "NonHeroAbilityLifestealTooltipOnly",
                value = JsonPrimitive(3),
            ),
        ),
    )
    val leech = ItemDto(
        key = "upgrade_damage_recycler",
        name = "Leech",
        description = "Reduces the effect of enemy applied <span class=\"highlight\">healing reduction</span>.",
        cost = 6400,
        tier = 4,
        slot = "Armor",
        activation = "Passive",
        components = listOf("upgrade_vampire", "upgrade_health_stealing_magic"),
        targetTypes = listOf("AllEnemy"),
        shopFilters = listOf("MagicDamage", "Healing"),
        isDisabled = false,
        streetBrawl = false,
        info1 = ItemInfoDto(
            type = "Innate",
            main = listOf(
                ItemPropDto(
                    key = "AbilityLifestealPercentHero",
                    value = JsonPrimitive(30),
                    type = "healing",
                ),
                ItemPropDto(
                    key = "BulletLifestealPercent",
                    value = JsonPrimitive(30),
                    type = "healing",
                ),
            ),
            alt = listOf(
                ItemPropDto(
                    key = "BonusHealth",
                    value = JsonPrimitive(120),
                    type = "health",
                ),
                ItemPropDto(
                    key = "BaseAttackDamagePercent",
                    value = JsonPrimitive(12),
                    type = "bullet_damage",
                ),
                ItemPropDto(
                    key = "TechPower",
                    value = JsonPrimitive(12),
                    type = "tech_damage",
                ),
            ),
        ),
        upgrades = mapOf(
            "BulletLifestealPercent" to JsonPrimitive(15),
            "AbilityLifestealPercentHero" to JsonPrimitive(15),
            "BonusHealth" to JsonPrimitive(100),
            "TechPower" to JsonPrimitive(15),
            "BaseAttackDamagePercent" to JsonPrimitive(15),
        ),
        other = mapOf(
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
        ),
    )
    val infuser = ItemDto(
        key = "upgrade_infuser",
        name = "Infuser",
        description = "Gain <span class=\"highlight\">Spirit Lifesteal</span> and <span class=\"highlight\">Spirit Power</span>.",
        cost = 6400,
        tier = 4,
        slot = "Armor",
        activation = "InstantCast",
        components = listOf("upgrade_health_stealing_magic"),
        targetTypes = null,
        shopFilters = listOf("MagicDamage"),
        isDisabled = false,
        streetBrawl = false,
        info1 = ItemInfoDto(
            type = "Innate",
            alt = listOf(
                ItemPropDto(
                    key = "AbilityLifestealPercentHeroPassive",
                    value = JsonPrimitive(16),
                    type = "healing",
                ),
                ItemPropDto(
                    key = "TechResist",
                    value = JsonPrimitive(10),
                    type = "tech_armor_up",
                    usageFlags = "IntrinsicallyProvidedInAbility",
                ),
                ItemPropDto(
                    key = "BonusHealth",
                    value = JsonPrimitive(100),
                ),
            ),
        ),
        info2 = ItemInfoDto(
            type = "Active",
            descKey = "#upgrade_infuser_desc",
            cooldown = 30.0,
            main = listOf(
                ItemPropDto(
                    key = "AbilityLifestealPercentHero",
                    value = JsonPrimitive(80),
                    type = "healing",
                    usageFlags = "ConditionallyApplied",
                ),
                ItemPropDto(
                    key = "BonusSpirit",
                    value = JsonPrimitive(30),
                    type = "tech_damage",
                    usageFlags = "ConditionallyApplied",
                ),
            ),
            alt = listOf(
                ItemPropDto(
                    key = "AbilityDuration",
                    value = JsonPrimitive(6),
                    type = "duration",
                ),
            ),
        ),
        upgrades = mapOf(
            "AbilityCooldown" to JsonPrimitive(-10),
            "TechResist" to JsonPrimitive(10),
            "BonusSpirit" to JsonPrimitive(30),
            "BonusHealth" to JsonPrimitive(50),
            "AbilityLifestealPercentHeroPassive" to JsonPrimitive(16),
        ),
        other = mapOf(
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
            "NonHeroAbilityLifestealTooltipOnly" to OtherDto(
                key = "NonHeroAbilityLifestealTooltipOnly",
                value = JsonPrimitive(3),
            ),
        ),
    )
}