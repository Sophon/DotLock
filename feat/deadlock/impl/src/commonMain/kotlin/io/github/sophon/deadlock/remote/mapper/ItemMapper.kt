package io.github.sophon.deadlock.remote.mapper

import io.github.sophon.core.domain.model.Bonus
import io.github.sophon.core.domain.model.Item
import io.github.sophon.core.domain.model.Url
import io.github.sophon.core.util.formKey
import io.github.sophon.deadlock.DeadlockFeatureInfo
import io.github.sophon.deadlock.remote.dto.ItemDto

internal fun Map.Entry<String, ItemDto>.toDomain(imageUrls: Map<String, String>): Item {
    val dto = value
    val key = key
    val itemKey = (value.name?.formKey() ?: key)

    val item = Item(
        key = itemKey,
        altKey = key,
        name = dto.name ?: key,
        url = Url(
            image = imageUrls[itemKey],
            wiki = dto.name.toWikiUrl(),
        ),

        description = dto.description,
        isStreetBrawl = dto.streetBrawl ?: false,
        shopInfo = dto.toDomainShop(),
        timing = Item.Timing(
            cooldown = dto.abilityCooldown.toScaledValue(),
            cooldownBetweenCharge = dto.abilityCooldownBetweenCharge.toScaledValue(),
            castDelay = dto.abilityCastDelay.toScaledValue(),
            postCastDuration = dto.abilityPostCastDuration.toScaledValue(),
            channelTime = dto.abilityChannelTime.toScaledValue(),
            channelMoveSpeed = dto.channelMoveSpeed.toScaledValue(),
            duration = dto.abilityDuration.toScaledValue(),
            charges = dto.abilityCharges.toScaledValue(),
        ),
        targeting = Item.Targeting(
            castRange = dto.abilityCastRange.toScaledValue(),
            unitTargetLimit = dto.abilityUnitTargetLimit.toScaledValue(),
        ),
        bonusSet = dto.toDomainBonus(),
    )

    return item
}

private fun ItemDto.toDomainShop(): Item.ShopInfo {
    val shopInfo = Item.ShopInfo(
        cost = this.cost ?: 0,
        tier = this.tier ?: 0,
        slot = when (this.slot.orEmpty()) {
            "Weapon" -> Item.ShopInfo.Slot.WEAPON
            "Armor" -> Item.ShopInfo.Slot.ARMOR
            "Tech" -> Item.ShopInfo.Slot.TECH
            else -> Item.ShopInfo.Slot.UNKNOWN
        },
        activation = when (this.activation.orEmpty()) {
            "Passive" -> Item.ShopInfo.Activation.PASSIVE
            "InstantCast" -> Item.ShopInfo.Activation.INSTANT_CAST
            "InstantCastToggle" -> Item.ShopInfo.Activation.INSTANT_CAST_TOGGLE
            "Press" -> Item.ShopInfo.Activation.PRESS
            "OnRelease" -> Item.ShopInfo.Activation.ON_RELEASE
            else -> Item.ShopInfo.Activation.UNKNOWN
        },
        targetTypeSet = this.toTargetTypeSet(),
        shopFilters = this.shopFilters.orEmpty().map { filter ->
            Item.ShopInfo.ShopFilter.fromString(filter)
        },
        components = this.components.orEmpty(),
    )
    return shopInfo
}

private fun ItemDto.toTargetTypeSet(): Set<Item.ShopInfo.TargetType> {
    val set = targetTypes
        ?.map { Item.ShopInfo.TargetType.fromString(it) }
        ?.toSet()
        ?: emptySet()
    return set
}

private fun ItemDto.toDomainBonus(): Set<Bonus> {
    val bonuses = mutableSetOf<Bonus>()

    damage.toScaledValue()?.let { bonuses.add(Bonus(Bonus.Type.DAMAGE, it)) }
    dps.toScaledValue()?.let { bonuses.add(Bonus(Bonus.Type.DPS, it)) }
    normalDps.toScaledValue()?.let { bonuses.add(Bonus(Bonus.Type.NORMAL_DPS, it)) }
    maxDps.toScaledValue()?.let { bonuses.add(Bonus(Bonus.Type.MAX_DPS, it)) }
    damageHeavyMelee.toScaledValue()?.let { bonuses.add(Bonus(Bonus.Type.DAMAGE_HEAVY_MELEE, it)) }
    spiritDamage.toScaledValue()?.let { bonuses.add(Bonus(Bonus.Type.DAMAGE_SPIRIT, it)) }
    impactDamage.toScaledValue()?.let { bonuses.add(Bonus(Bonus.Type.DAMAGE_IMPACT, it)) }
    headShotBonusDamage.toScaledValue()?.let { bonuses.add(Bonus(Bonus.Type.DAMAGE_BONUS_HEADSHOT, it)) }
    dotHealthPercent.toScaledValue()?.let { bonuses.add(Bonus(Bonus.Type.DOT_HP_PCT, it)) }
    damagePulseAmount.toScaledValue()?.let { bonuses.add(Bonus(Bonus.Type.DAMAGE_PULSE_AMOUNT, it)) }
    dpsIncrease.toScaledValue()?.let { bonuses.add(Bonus(Bonus.Type.DPS_INCREASE, it)) }
    dpsMax.toScaledValue()?.let { bonuses.add(Bonus(Bonus.Type.DPS_MAX, it)) }
    damagePerChain.toScaledValue()?.let { bonuses.add(Bonus(Bonus.Type.DAMAGE_P_CHAIN, it)) }
    procBaseAttackDamagePercent.toScaledValue()?.let { bonuses.add(Bonus(Bonus.Type.PROC_DAMAGE_ATTACK_DAMAGE_BASE_PCT, it)) }
    procBaseAttackDamagePercentAltFire.toScaledValue()?.let { bonuses.add(Bonus(Bonus.Type.PROC_DAMAGE_ATTACK_DAMAGE_BASE_ALT_PCT, it)) }
    bonusHealth.toScaledValue()?.let { bonuses.add(Bonus(Bonus.Type.HP_BONUS, it)) }
    totalHealthRegen.toScaledValue()?.let { bonuses.add(Bonus(Bonus.Type.HP_REGEN_TOTAL, it)) }
    healPerStack.toScaledValue()?.let { bonuses.add(Bonus(Bonus.Type.HEAL_P_STACK, it)) }
    lifestrikeHeal.toScaledValue()?.let { bonuses.add(Bonus(Bonus.Type.HEAL_LIFE_STRIKE, it)) }
    lifestealHeal.toScaledValue()?.let { bonuses.add(Bonus(Bonus.Type.HEAL_LIFE_STEAL, it)) }
    lifestealHealPercent.toScaledValue()?.let { bonuses.add(Bonus(Bonus.Type.HEAL_LIFE_STEAL_PCT, it)) }
    healPercentPerHeadshot.toScaledValue()?.let { bonuses.add(Bonus(Bonus.Type.HEAL_P_HEADSHOT_PCT, it)) }
    healingPerCast.toScaledValue()?.let { bonuses.add(Bonus(Bonus.Type.HEAL_P_CAST, it)) }
    regeneration.toScaledValue()?.let { bonuses.add(Bonus(Bonus.Type.REGEN, it)) }
    healOnVeil.toScaledValue()?.let { bonuses.add(Bonus(Bonus.Type.HEAL_ON_VEIL, it)) }
    vexBarrierCombatBarrier.toScaledValue()?.let { bonuses.add(Bonus(Bonus.Type.VEX_BARRIER_COMBAT_BARRIER, it)) }
    combatBarrier.toScaledValue()?.let { bonuses.add(Bonus(Bonus.Type.COMBAT_BARRIER, it)) }
    bonusPerChain.toScaledValue()?.let { bonuses.add(Bonus(Bonus.Type.BONUS_P_CHAIN, it)) }
    baseAttackDamagePercent.toScaledValue()?.let { bonuses.add(Bonus(Bonus.Type.DAMAGE_BASE_ATTACK_PCT, it)) }
    bulletArmorReduction.toScaledValue()?.let { bonuses.add(Bonus(Bonus.Type.ARMOR_REDUCTION_BULLET, it)) }
    techPower.toScaledValue()?.let { bonuses.add(Bonus(Bonus.Type.TECH_POWER, it)) }
    techResist.toScaledValue()?.let { bonuses.add(Bonus(Bonus.Type.TECH_RESIST, it)) }
    bulletResist.toScaledValue()?.let { bonuses.add(Bonus(Bonus.Type.BULLET_RESIST, it)) }
    bonusFireRate.toScaledValue()?.let { bonuses.add(Bonus(Bonus.Type.BONUS_FIRE_RATE, it)) }
    bonusHealthRegen.toScaledValue()?.let { bonuses.add(Bonus(Bonus.Type.BONUS_HP_REG, it)) }
    outOfCombatHealthRegen.toScaledValue()?.let { bonuses.add(Bonus(Bonus.Type.OOC_HP_REG, it)) }
    stamina.toScaledValue()?.let { bonuses.add(Bonus(Bonus.Type.STAMINA, it)) }
    staminaCooldownReduction.toScaledValue()?.let { bonuses.add(Bonus(Bonus.Type.STAMINA_CD_RED, it)) }
    cooldownReduction.toScaledValue()?.let { bonuses.add(Bonus(Bonus.Type.CD_REDUCTION, it)) }
    bonusClipSize.toScaledValue()?.let { bonuses.add(Bonus(Bonus.Type.BONUS_CLIP_SIZE, it)) }
    bonusClipSizePercent.toScaledValue()?.let { bonuses.add(Bonus(Bonus.Type.BONUS_CLIP_SIZE_PCT, it)) }
    bonusBulletSpeedPercent.toScaledValue()?.let { bonuses.add(Bonus(Bonus.Type.BONUS_BULLET_SPEED_PCT, it)) }
    bulletLifestealPercent.toScaledValue()?.let { bonuses.add(Bonus(Bonus.Type.BULLET_LIFE_STEAL_PCT, it)) }
    abilityLifestealPercentHero.toScaledValue()?.let { bonuses.add(Bonus(Bonus.Type.ABILITY_LIFE_STEAL_HERO_PCT, it)) }
    slowPercent.toScaledValue()?.let { bonuses.add(Bonus(Bonus.Type.SLOW_PCT, it)) }
    slowDuration.toScaledValue()?.let { bonuses.add(Bonus(Bonus.Type.SLOW_DURATION, it)) }
    statusResistancePercent.toScaledValue()?.let { bonuses.add(Bonus(Bonus.Type.STATUS_RESIST_PCT, it)) }
    procChance.toScaledValue()?.let { bonuses.add(Bonus(Bonus.Type.PROC_CHANCE, it)) }
    techRangeMultiplier.toScaledValue()?.let { bonuses.add(Bonus(Bonus.Type.TECH_RANGE_MULT, it)) }
    techRadiusMultiplier.toScaledValue()?.let { bonuses.add(Bonus(Bonus.Type.TECH_RADIUS_MULT, it)) }
    bonusAbilityDurationPercent.toScaledValue()?.let { bonuses.add(Bonus(Bonus.Type.BONUS_ABILITY_DUR_PCT, it)) }
    bonusAbilityCharges.toScaledValue()?.let { bonuses.add(Bonus(Bonus.Type.BONUS_ABILITY_CHARGE, it)) }
    abilityCooldown.toScaledValue()?.let { bonuses.add(Bonus(Bonus.Type.ABILITY_CD, it)) }
    magicResistReduction.toScaledValue()?.let { bonuses.add(Bonus(Bonus.Type.MAGIC_RESIST_RED, it)) }
    healAmpReceivePenaltyPercent.toScaledValue()?.let { bonuses.add(Bonus(Bonus.Type.HEAL_AMP_RECEIVE_PENALTY_PCT, it)) }
    healAmpRegenPenaltyPercent.toScaledValue()?.let { bonuses.add(Bonus(Bonus.Type.HEAL_AMP_REGEN_PENALTY_PCT, it)) }
    fireRateSlow.toScaledValue()?.let { bonuses.add(Bonus(Bonus.Type.FIRE_RATE_SLOW, it)) }
    bonusMeleeDamagePercent.toScaledValue()?.let { bonuses.add(Bonus(Bonus.Type.BONUS_MELEE_DAMAGE_PCT, it)) }
    techPowerPercent.toScaledValue()?.let { bonuses.add(Bonus(Bonus.Type.TECH_POWER_PCT, it)) }
    bonusMoveSpeed.toScaledValue()?.let { bonuses.add(Bonus(Bonus.Type.BONUS_MS, it)) }
    bonusSprintSpeed.toScaledValue()?.let { bonuses.add(Bonus(Bonus.Type.BONUS_SPRINT, it)) }

    return bonuses.toSet()
}

private fun String?.toWikiUrl(): String? {
    if (this == null) return null
    val result = "${DeadlockFeatureInfo.featureInfo.url}/${this.replace(" ", "_")}"
    return result
}
