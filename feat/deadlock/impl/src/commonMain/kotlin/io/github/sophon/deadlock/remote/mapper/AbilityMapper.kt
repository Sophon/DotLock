package io.github.sophon.deadlock.remote.mapper

import io.github.sophon.core.domain.model.Ability
import io.github.sophon.core.domain.model.Property
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


private fun AbilityDto.toDomainPropertySet(): Set<Property> {
    val properties = mutableSetOf<Property>()

//    damage.toScaledValue()?.let { properties.add(Property(Property.Key.DAMAGE, it)) }
//    dps.toScaledValue()?.let { properties.add(Property(Property.Key.DPS, it)) }
//    normalDps.toScaledValue()?.let { properties.add(Property(Property.Key.NORMAL_DPS, it)) }
//    maxDps.toScaledValue()?.let { properties.add(Property(Property.Key.MAX_DPS, it)) }
//    damageHeavyMelee.toScaledValue()?.let { properties.add(Property(Property.Key.DAMAGE_HEAVY_MELEE, it)) }
//    bonusDamage.toScaledValue()?.let { properties.add(Property(Property.Key.BONUS_DAMAGE, it)) }
//    combatBarrier.toScaledValue()?.let { properties.add(Property(Property.Key.COMBAT_BARRIER, it)) }
//    healAmount.toScaledValue()?.let { properties.add(Property(Property.Key.HEAL_AMOUNT, it)) }
//    impactDamage.toScaledValue()?.let { properties.add(Property(Property.Key.IMPACT_DAMAGE, it)) }
//    explosionDamage.toScaledValue()?.let { properties.add(Property(Property.Key.EXPLOSION_DAMAGE, it)) }
//    maxDamage.toScaledValue()?.let { properties.add(Property(Property.Key.MAX_DAMAGE, it)) }
//    minDamage.toScaledValue()?.let { properties.add(Property(Property.Key.MIN_DAMAGE, it)) }
//    radius.toScaledValue()?.let { properties.add(Property(Property.Key.RADIUS, it)) }
//    tickRate.toScaledValue()?.let { properties.add(Property(Property.Key.TICK_RATE, it)) }
//    slowPercent.toScaledValue()?.let { properties.add(Property(Property.Key.SLOW_PERCENT, it)) }
//    slowDuration.toScaledValue()?.let { properties.add(Property(Property.Key.SLOW_DURATION, it)) }
//    stunDuration.toScaledValue()?.let { properties.add(Property(Property.Key.STUN_DURATION, it)) }
//    debuffDuration.toScaledValue()?.let { properties.add(Property(Property.Key.DEBUFF_DURATION, it)) }
//    buffDuration.toScaledValue()?.let { properties.add(Property(Property.Key.BUFF_DURATION, it)) }
//    immobilizeDuration.toScaledValue()?.let { properties.add(Property(Property.Key.IMMOBILIZE_DURATION, it)) }
//    bonusMoveSpeed.toScaledValue()?.let { properties.add(Property(Property.Key.BONUS_MOVE_SPEED, it)) }
//    bonusFireRate.toScaledValue()?.let { properties.add(Property(Property.Key.BONUS_FIRE_RATE, it)) }
//    bulletResist.toScaledValue()?.let { properties.add(Property(Property.Key.BULLET_RESIST, it)) }
//    techResist.toScaledValue()?.let { properties.add(Property(Property.Key.TECH_RESIST, it)) }
//    maxStacks.toScaledValue()?.let { properties.add(Property(Property.Key.MAX_STACKS, it)) }

    return properties
}
