package io.github.sophon.deadlock.remote.mapper

import io.github.sophon.core.domain.model.Ability
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
        timing = dto.toDomainTiming(),
        targeting = dto.toDomainTargeting(),
        propertySet = dto.toDomainPropertySet(),
        upgrades = emptyList(),
    )

    return ability
}

private fun AbilityDto.toDomainTiming(): Ability.Timing {
    val timing = Ability.Timing(
        cooldown = abilityCooldown?.toScaledValue(),
        cooldownBetweenCharge = abilityCooldownBetweenCharge?.toScaledValue(),
        castDelay = abilityCastDelay?.toScaledValue(),
        postCastDuration = abilityPostCastDuration?.toScaledValue(),
        channelTime = abilityChannelTime?.toScaledValue(),
        channelMoveSpeed = channelMoveSpeed?.toScaledValue(),
        duration = abilityDuration?.toScaledValue(),
        charges = abilityCharges?.toScaledValue(),
    )

    return timing
}

private fun AbilityDto.toDomainTargeting(): Ability.Targeting {
    val targeting = Ability.Targeting(
        castRange = abilityCastRange?.toScaledValue(),
        unitTargetLimit = abilityUnitTargetLimit,
    )

    return targeting
}

private fun AbilityDto.toDomainPropertySet(): Set<Ability.Property> {
    val properties = mutableSetOf<Ability.Property>()

    damage.toScaledValue()?.let { properties.add(Ability.Property(Ability.Property.Type.DAMAGE, it)) }
    dps.toScaledValue()?.let { properties.add(Ability.Property(Ability.Property.Type.DPS, it)) }
    normalDps.toScaledValue()?.let { properties.add(Ability.Property(Ability.Property.Type.NORMAL_DPS, it)) }
    maxDps.toScaledValue()?.let { properties.add(Ability.Property(Ability.Property.Type.MAX_DPS, it)) }
    damageHeavyMelee.toScaledValue()?.let { properties.add(Ability.Property(Ability.Property.Type.DAMAGE_HEAVY_MELEE, it)) }
    bonusDamage.toScaledValue()?.let { properties.add(Ability.Property(Ability.Property.Type.BONUS_DAMAGE, it)) }
    combatBarrier.toScaledValue()?.let { properties.add(Ability.Property(Ability.Property.Type.COMBAT_BARRIER, it)) }
    healAmount.toScaledValue()?.let { properties.add(Ability.Property(Ability.Property.Type.HEAL_AMOUNT, it)) }
    impactDamage.toScaledValue()?.let { properties.add(Ability.Property(Ability.Property.Type.IMPACT_DAMAGE, it)) }
    explosionDamage.toScaledValue()?.let { properties.add(Ability.Property(Ability.Property.Type.EXPLOSION_DAMAGE, it)) }
    maxDamage.toScaledValue()?.let { properties.add(Ability.Property(Ability.Property.Type.MAX_DAMAGE, it)) }
    minDamage.toScaledValue()?.let { properties.add(Ability.Property(Ability.Property.Type.MIN_DAMAGE, it)) }
    radius.toScaledValue()?.let { properties.add(Ability.Property(Ability.Property.Type.RADIUS, it)) }
    tickRate.toScaledValue()?.let { properties.add(Ability.Property(Ability.Property.Type.TICK_RATE, it)) }
    slowPercent.toScaledValue()?.let { properties.add(Ability.Property(Ability.Property.Type.SLOW_PERCENT, it)) }
    slowDuration.toScaledValue()?.let { properties.add(Ability.Property(Ability.Property.Type.SLOW_DURATION, it)) }
    stunDuration.toScaledValue()?.let { properties.add(Ability.Property(Ability.Property.Type.STUN_DURATION, it)) }
    debuffDuration.toScaledValue()?.let { properties.add(Ability.Property(Ability.Property.Type.DEBUFF_DURATION, it)) }
    buffDuration.toScaledValue()?.let { properties.add(Ability.Property(Ability.Property.Type.BUFF_DURATION, it)) }
    immobilizeDuration.toScaledValue()?.let { properties.add(Ability.Property(Ability.Property.Type.IMMOBILIZE_DURATION, it)) }
    bonusMoveSpeed.toScaledValue()?.let { properties.add(Ability.Property(Ability.Property.Type.BONUS_MOVE_SPEED, it)) }
    bonusFireRate.toScaledValue()?.let { properties.add(Ability.Property(Ability.Property.Type.BONUS_FIRE_RATE, it)) }
    bulletResist.toScaledValue()?.let { properties.add(Ability.Property(Ability.Property.Type.BULLET_RESIST, it)) }
    techResist.toScaledValue()?.let { properties.add(Ability.Property(Ability.Property.Type.TECH_RESIST, it)) }
    maxStacks.toScaledValue()?.let { properties.add(Ability.Property(Ability.Property.Type.MAX_STACKS, it)) }

    return properties
}
