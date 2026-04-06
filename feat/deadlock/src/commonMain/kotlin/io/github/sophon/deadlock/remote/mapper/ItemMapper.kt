package io.github.sophon.deadlock.remote.mapper

import io.github.sophon.core.domain.model.Item
import io.github.sophon.core.domain.model.ScaledValue
import io.github.sophon.core.domain.model.ShopInfo
import io.github.sophon.core.util.toEnumOrDefault
import io.github.sophon.deadlock.remote.dto.ItemDto
import io.github.sophon.deadlock.remote.dto.ScaledValueDto

internal fun Map.Entry<String, ItemDto>.toDomain(): Item {
    val dto = value
    val key = key

    val item = Item(
        key = key,
        name = dto.name ?: key,
        description = dto.description,
        isDisabled = dto.isDisabled ?: false,
        isStreetBrawl = dto.streetBrawl ?: false,
        shop = dto.toDomainShop(),
        timing = Item.Timing(
            cooldown = dto.abilityCooldown ?: 0.0,
            cooldownBetweenCharge = dto.abilityCooldownBetweenCharge ?: 0.0,
            castDelay = dto.abilityCastDelay ?: 0.0,
            postCastDuration = dto.abilityPostCastDuration ?: 0.0,
            channelTime = dto.abilityChannelTime ?: 0.0,
            channelMoveSpeed = dto.channelMoveSpeed ?: 0.0,
            duration = dto.abilityDuration ?: 0.0,
            charges = dto.abilityCharges ?: 0,
        ),
        targeting = Item.Targeting(
            castRange = dto.abilityCastRange ?: 0.0,
            unitTargetLimit = dto.abilityUnitTargetLimit ?: 0,
        ),
        bonusSet = dto.toDomainBonus(),
    )

    return item
}

private fun ItemDto.toDomainShop(): ShopInfo {
    val shopInfo = ShopInfo(
        cost = this.cost ?: 0,
        tier = this.tier ?: 0,
        slot = when (this.slot.orEmpty()) {
            "Weapon" -> ShopInfo.Slot.WEAPON
            "Armor" -> ShopInfo.Slot.ARMOR
            "Tech" -> ShopInfo.Slot.TECH
            else -> ShopInfo.Slot.UNKNOWN
        },
        activation = when (this.activation.orEmpty()) {
            "Passive" -> ShopInfo.Activation.PASSIVE
            "InstantCast" -> ShopInfo.Activation.INSTANT_CAST
            "InstantCastToggle" -> ShopInfo.Activation.INSTANT_CAST_TOGGLE
            "Press" -> ShopInfo.Activation.PRESS
            "OnRelease" -> ShopInfo.Activation.ON_RELEASE
            else -> ShopInfo.Activation.UNKNOWN
        },
        targetTypeSet = this.toTargetTypeSet(),
        shopFilters = this.shopFilters.orEmpty().map { filter ->
            ShopInfo.ShopFilter.fromString(filter)
        },
        components = this.components.orEmpty(),
    )
    return shopInfo
}

private fun ItemDto.toTargetTypeSet(): Set<ShopInfo.TargetType> {
    val set = targetTypes
        ?.map { ShopInfo.TargetType.fromString(it) }
        ?.toSet()
        ?: emptySet()
    return set
}

private fun ItemDto.toDomainBonus(): Set<Item.Bonus> {
    val bonuses = mutableSetOf<Item.Bonus>()

    damage?.toDomain()?.let { bonuses.add(Item.Bonus(Item.Bonus.Type.DAMAGE, it)) }
    dps?.toDomain()?.let { bonuses.add(Item.Bonus(Item.Bonus.Type.DPS, it)) }
    spiritDamage?.toDomain()?.let { bonuses.add(Item.Bonus(Item.Bonus.Type.DAMAGE_SPIRIT, it)) }
    impactDamage?.toDomain()?.let { bonuses.add(Item.Bonus(Item.Bonus.Type.DAMAGE_IMPACT, it)) }
    headShotBonusDamage?.toDomain()?.let { bonuses.add(Item.Bonus(Item.Bonus.Type.DAMAGE_BONUS_HEADSHOT, it)) }
    dotHealthPercent?.toDomain()?.let { bonuses.add(Item.Bonus(Item.Bonus.Type.DOT_HP_PCT, it)) }
    damagePulseAmount?.toDomain()?.let { bonuses.add(Item.Bonus(Item.Bonus.Type.DAMAGE_PULSE_AMOUNT, it)) }
    dpsIncrease?.toDomain()?.let { bonuses.add(Item.Bonus(Item.Bonus.Type.DPS_INCREASE, it)) }
    dpsMax?.toDomain()?.let { bonuses.add(Item.Bonus(Item.Bonus.Type.DPS_MAX, it)) }
    damagePerChain?.toDomain()?.let { bonuses.add(Item.Bonus(Item.Bonus.Type.DAMAGE_P_CHAIN, it)) }
    procBaseAttackDamagePercent?.toDomain()?.let { bonuses.add(Item.Bonus(Item.Bonus.Type.PROC_DAMAGE_ATTACK_DAMAGE_BASE_PCT, it)) }
    procBaseAttackDamagePercentAltFire?.toDomain()?.let { bonuses.add(Item.Bonus(Item.Bonus.Type.PROC_DAMAGE_ATTACK_DAMAGE_BASE_ALT_PCT, it)) }
    bonusHealth?.toDomain()?.let { bonuses.add(Item.Bonus(Item.Bonus.Type.HP_BONUS, it)) }
    totalHealthRegen?.toDomain()?.let { bonuses.add(Item.Bonus(Item.Bonus.Type.HP_REGEN_TOTAL, it)) }
    healPerStack?.toDomain()?.let { bonuses.add(Item.Bonus(Item.Bonus.Type.HEAL_P_STACK, it)) }
    lifestrikeHeal?.toDomain()?.let { bonuses.add(Item.Bonus(Item.Bonus.Type.HEAL_LIFE_STRIKE, it)) }
    lifestealHeal?.toDomain()?.let { bonuses.add(Item.Bonus(Item.Bonus.Type.HEAL_LIFE_STEAL, it)) }
    lifestealHealPercent?.toDomain()?.let { bonuses.add(Item.Bonus(Item.Bonus.Type.HEAL_LIFE_STEAL_PCT, it)) }
    healPercentPerHeadshot?.toDomain()?.let { bonuses.add(Item.Bonus(Item.Bonus.Type.HEAL_P_HEADSHOT_PCT, it)) }
    healingPerCast?.toDomain()?.let { bonuses.add(Item.Bonus(Item.Bonus.Type.HEAL_P_CAST, it)) }
    regeneration?.toDomain()?.let { bonuses.add(Item.Bonus(Item.Bonus.Type.REGEN, it)) }
    healOnVeil?.toDomain()?.let { bonuses.add(Item.Bonus(Item.Bonus.Type.HEAL_ON_VEIL, it)) }
    vexBarrierCombatBarrier?.toDomain()?.let { bonuses.add(Item.Bonus(Item.Bonus.Type.VEX_BARRIER_COMBAT_BARRIER, it)) }
    combatBarrier?.toDomain()?.let { bonuses.add(Item.Bonus(Item.Bonus.Type.COMBAT_BARRIER, it)) }
    bonusPerChain?.toDomain()?.let { bonuses.add(Item.Bonus(Item.Bonus.Type.BONUS_P_CHAIN, it)) }

    baseAttackDamagePercent?.let { bonuses.add(Item.Bonus(Item.Bonus.Type.DAMAGE_BASE_ATTACK_PCT, it.toScaledValue())) }
    bulletArmorReduction?.let { bonuses.add(Item.Bonus(Item.Bonus.Type.ARMOR_REDUCTION_BULLET, it.toScaledValue())) }
    techPower?.let { bonuses.add(Item.Bonus(Item.Bonus.Type.TECH_POWER, it.toScaledValue())) }
    techResist?.let { bonuses.add(Item.Bonus(Item.Bonus.Type.TECH_RESIST, it.toScaledValue())) }
    bulletResist?.let { bonuses.add(Item.Bonus(Item.Bonus.Type.BULLET_RESIST, it.toScaledValue())) }
    bonusFireRate?.let { bonuses.add(Item.Bonus(Item.Bonus.Type.BONUS_FIRE_RATE, it.toScaledValue())) }
    bonusHealthRegen?.let { bonuses.add(Item.Bonus(Item.Bonus.Type.BONUS_HP_REG, it.toScaledValue())) }
    outOfCombatHealthRegen?.let { bonuses.add(Item.Bonus(Item.Bonus.Type.OOC_HP_REG, it.toScaledValue())) }
    stamina?.let { bonuses.add(Item.Bonus(Item.Bonus.Type.STAMINA, it.toDouble().toScaledValue())) }
    staminaCooldownReduction?.let { bonuses.add(Item.Bonus(Item.Bonus.Type.STAMINA_CD_RED, it.toScaledValue())) }
    cooldownReduction?.let { bonuses.add(Item.Bonus(Item.Bonus.Type.CD_REDUCTION, it.toScaledValue())) }
    bonusClipSize?.let { bonuses.add(Item.Bonus(Item.Bonus.Type.BONUS_CLIP_SIZE, it.toDouble().toScaledValue())) }
    bonusClipSizePercent?.let { bonuses.add(Item.Bonus(Item.Bonus.Type.BONUS_CLIP_SIZE_PCT, it.toScaledValue())) }
    bonusBulletSpeedPercent?.let { bonuses.add(Item.Bonus(Item.Bonus.Type.BONUS_BULLET_SPEED_PCT, it.toScaledValue())) }
    bulletLifestealPercent?.let { bonuses.add(Item.Bonus(Item.Bonus.Type.BULLET_LIFE_STEAL_PCT, it.toScaledValue())) }
    abilityLifestealPercentHero?.let { bonuses.add(Item.Bonus(Item.Bonus.Type.ABILITY_LIFE_STEAL_HERO_PCT, it.toScaledValue())) }
    slowPercent?.let { bonuses.add(Item.Bonus(Item.Bonus.Type.SLOW_PCT, it.toScaledValue())) }
    slowDuration?.let { bonuses.add(Item.Bonus(Item.Bonus.Type.SLOW_DUR, it.toScaledValue())) }
    statusResistancePercent?.let { bonuses.add(Item.Bonus(Item.Bonus.Type.STATUS_RESIST_PCT, it.toScaledValue())) }
    procChance?.let { bonuses.add(Item.Bonus(Item.Bonus.Type.PROC_CHANCE, it.toScaledValue())) }
    techRangeMultiplier?.let { bonuses.add(Item.Bonus(Item.Bonus.Type.TECH_RANGE_MULT, it.toScaledValue())) }
    techRadiusMultiplier?.let { bonuses.add(Item.Bonus(Item.Bonus.Type.TECH_RADIUS_MULT, it.toScaledValue())) }
    bonusAbilityDurationPercent?.let { bonuses.add(Item.Bonus(Item.Bonus.Type.BONUS_ABILITY_DUR_PCT, it.toScaledValue())) }
    bonusAbilityCharges?.let { bonuses.add(Item.Bonus(Item.Bonus.Type.BONUS_ABILITY_CHARGE, it.toDouble().toScaledValue())) }
    abilityCooldown?.let { bonuses.add(Item.Bonus(Item.Bonus.Type.ABILITY_CD, it.toScaledValue())) }
    magicResistReduction?.let { bonuses.add(Item.Bonus(Item.Bonus.Type.MAGIC_RESIST_RED, it.toScaledValue())) }
    healAmpReceivePenaltyPercent?.let { bonuses.add(Item.Bonus(Item.Bonus.Type.HEAL_AMP_RECEIVE_PENALTY_PCT, it.toScaledValue())) }
    healAmpRegenPenaltyPercent?.let { bonuses.add(Item.Bonus(Item.Bonus.Type.HEAL_AMP_REGEN_PENALTY_PCT, it.toScaledValue())) }
    fireRateSlow?.let { bonuses.add(Item.Bonus(Item.Bonus.Type.FIRE_RATE_SLOW, it.toScaledValue())) }
    bonusMeleeDamagePercent?.let { bonuses.add(Item.Bonus(Item.Bonus.Type.BONUS_MELEE_DAMAGE_PCT, it.toScaledValue())) }
    techPowerPercent?.let { bonuses.add(Item.Bonus(Item.Bonus.Type.TECH_POWER_PCT, it.toScaledValue())) }
    bonusMoveSpeed?.parseDouble()?.let { bonuses.add(Item.Bonus(Item.Bonus.Type.BONUS_MS, it.toScaledValue())) }
    bonusSprintSpeed?.parseDouble()?.let { bonuses.add(Item.Bonus(Item.Bonus.Type.BONUS_SPRINT, it.toScaledValue())) }

    return bonuses.toSet()
}

private fun String.parseDouble(): Double? {
    return this
        .filter { it.isDigit() || it == '.' }
        .toDoubleOrNull()
}

private fun ScaledValueDto.toDomain(): ScaledValue {
    val domainScale = scale?.value?.let { scaleValue ->
        ScaledValue.Scale(
            value = scaleValue,
            type = scale.type.toEnumOrDefault(ScaledValue.ScaleType.DAMAGE)
        )
    }

    val result = ScaledValue(
        value = value ?: 0.0,
        scale = domainScale
    )
    return result
}

private fun Double.toScaledValue(): ScaledValue {
    val result = ScaledValue(
        value = this,
        scale = null
    )
    return result
}