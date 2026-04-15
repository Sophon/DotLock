package io.github.sophon.deadlock.remote.mapper

import io.github.sophon.core.domain.model.Ability
import io.github.sophon.core.domain.model.Bonus
import io.github.sophon.core.util.formKey
import io.github.sophon.deadlock.remote.dto.AbilityDto

internal fun Map.Entry<String, AbilityDto>.toDomain(imageUrls: Map<String, String>): Ability {
    val dto = value
    val key = key
    val abilityKey = (value.name?.formKey()) ?: key

    val ability = Ability(
        key = abilityKey,
        altKey = key,
        name = dto.name ?: key,
        imageUrl = imageUrls[abilityKey],
        bonusSet = dto.toDomainPropertySet(),
        property = Ability.Property(
            channelTime = dto.abilityChannelTime.toScaledValue(),
            chargeCount = dto.abilityCharges.toScaledValue(),
            chargeCooldown = dto.abilityCooldownBetweenCharge.toScaledValue(),
            cooldown = dto.abilityCooldown.toScaledValue(),
            castRange = dto.abilityCastRange.toScaledValue(),
            duration = dto.abilityDuration.toScaledValue(),
            radius = dto.radius.toScaledValue(),
        )
    )

    return ability
}


private fun AbilityDto.toDomainPropertySet(): Set<Bonus> {
    val properties = mutableSetOf<Bonus>()

    damage.toScaledValue()?.let { properties.add(Bonus(Bonus.Type.DAMAGE, it)) }
    dps.toScaledValue()?.let { properties.add(Bonus(Bonus.Type.DPS, it)) }
    normalDps.toScaledValue()?.let { properties.add(Bonus(Bonus.Type.NORMAL_DPS, it)) }
    maxDps.toScaledValue()?.let { properties.add(Bonus(Bonus.Type.MAX_DPS, it)) }
    damageHeavyMelee.toScaledValue()?.let { properties.add(Bonus(Bonus.Type.DAMAGE_HEAVY_MELEE, it)) }
    bonusDamage.toScaledValue()?.let { properties.add(Bonus(Bonus.Type.BONUS_DAMAGE, it)) }
    combatBarrier.toScaledValue()?.let { properties.add(Bonus(Bonus.Type.COMBAT_BARRIER, it)) }
    healAmount.toScaledValue()?.let { properties.add(Bonus(Bonus.Type.HEAL_AMOUNT, it)) }
    impactDamage.toScaledValue()?.let { properties.add(Bonus(Bonus.Type.IMPACT_DAMAGE, it)) }
    explosionDamage.toScaledValue()?.let { properties.add(Bonus(Bonus.Type.EXPLOSION_DAMAGE, it)) }
    maxDamage.toScaledValue()?.let { properties.add(Bonus(Bonus.Type.MAX_DAMAGE, it)) }
    minDamage.toScaledValue()?.let { properties.add(Bonus(Bonus.Type.MIN_DAMAGE, it)) }
    radius.toScaledValue()?.let { properties.add(Bonus(Bonus.Type.RADIUS, it)) }
    tickRate.toScaledValue()?.let { properties.add(Bonus(Bonus.Type.TICK_RATE, it)) }
    slowPercent.toScaledValue()?.let { properties.add(Bonus(Bonus.Type.SLOW_PERCENT, it)) }
    slowDuration.toScaledValue()?.let { properties.add(Bonus(Bonus.Type.SLOW_DURATION, it)) }
    stunDuration.toScaledValue()?.let { properties.add(Bonus(Bonus.Type.STUN_DURATION, it)) }
    debuffDuration.toScaledValue()?.let { properties.add(Bonus(Bonus.Type.DEBUFF_DURATION, it)) }
    buffDuration.toScaledValue()?.let { properties.add(Bonus(Bonus.Type.BUFF_DURATION, it)) }
    immobilizeDuration.toScaledValue()?.let { properties.add(Bonus(Bonus.Type.IMMOBILIZE_DURATION, it)) }
    bonusMoveSpeed.toScaledValue()?.let { properties.add(Bonus(Bonus.Type.BONUS_MOVE_SPEED, it)) }
    bonusFireRate.toScaledValue()?.let { properties.add(Bonus(Bonus.Type.BONUS_FIRE_RATE, it)) }
    bulletResist.toScaledValue()?.let { properties.add(Bonus(Bonus.Type.BULLET_RESIST, it)) }
    techResist.toScaledValue()?.let { properties.add(Bonus(Bonus.Type.TECH_RESIST, it)) }
    maxStacks.toScaledValue()?.let { properties.add(Bonus(Bonus.Type.MAX_STACKS, it)) }

    return properties
}
