package io.github.sophon.deadlock.remote.mapper

import io.github.sophon.core.domain.model.Ability
import io.github.sophon.deadlock.remote.dto.AbilityDto
import io.github.sophon.deadlock.remote.dto.ScaledValueDto
import io.github.sophon.core.domain.model.ScaledValue
import io.github.sophon.core.util.formKey

internal fun Map.Entry<String, AbilityDto>.toDomain(imageUrls: Map<String, String>): Ability {
    val dto = value
    val key = key
    val abilityKey = (value.name?.formKey()) ?: key

    val ability = Ability(
        key = abilityKey,
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
        cooldownBetweenCharge = abilityCooldownBetweenCharge,
        castDelay = abilityCastDelay,
        postCastDuration = abilityPostCastDuration,
        channelTime = abilityChannelTime?.toScaledValue(),
        channelMoveSpeed = channelMoveSpeed,
        duration = abilityDuration?.toScaledValue(),
        charges = abilityCharges,
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

    damage?.toDomain()?.let { properties.add(Ability.Property(Ability.Property.Type.DAMAGE, it)) }
    dps?.toDomain()?.let { properties.add(Ability.Property(Ability.Property.Type.DPS, it)) }
    bonusDamage?.toDomain()?.let { properties.add(Ability.Property(Ability.Property.Type.BONUS_DAMAGE, it)) }
    combatBarrier?.toDomain()?.let { properties.add(Ability.Property(Ability.Property.Type.COMBAT_BARRIER, it)) }
    healAmount?.toDomain()?.let { properties.add(Ability.Property(Ability.Property.Type.HEAL_AMOUNT, it)) }
    impactDamage?.toDomain()?.let { properties.add(Ability.Property(Ability.Property.Type.IMPACT_DAMAGE, it)) }
    explosionDamage?.toDomain()?.let { properties.add(Ability.Property(Ability.Property.Type.EXPLOSION_DAMAGE, it)) }
    maxDamage?.toDomain()?.let { properties.add(Ability.Property(Ability.Property.Type.MAX_DAMAGE, it)) }
    minDamage?.toDomain()?.let { properties.add(Ability.Property(Ability.Property.Type.MIN_DAMAGE, it)) }
    radius?.let { properties.add(Ability.Property(Ability.Property.Type.RADIUS, it.toScaledValue())) }
    tickRate?.let { properties.add(Ability.Property(Ability.Property.Type.TICK_RATE, it.toScaledValue())) }
    slowPercent?.let { properties.add(Ability.Property(Ability.Property.Type.SLOW_PERCENT, it.toScaledValue())) }
    slowDuration?.let { properties.add(Ability.Property(Ability.Property.Type.SLOW_DURATION, it.toScaledValue())) }
    stunDuration?.let { properties.add(Ability.Property(Ability.Property.Type.STUN_DURATION, it.toScaledValue())) }
    debuffDuration?.let { properties.add(Ability.Property(Ability.Property.Type.DEBUFF_DURATION, it.toScaledValue())) }
    buffDuration?.let { properties.add(Ability.Property(Ability.Property.Type.BUFF_DURATION, it.toScaledValue())) }
    immobilizeDuration?.let { properties.add(Ability.Property(Ability.Property.Type.IMMOBILIZE_DURATION, it.toScaledValue())) }
    bonusMoveSpeed?.let { properties.add(Ability.Property(Ability.Property.Type.BONUS_MOVE_SPEED, it.toScaledValue())) }
    bonusFireRate?.let { properties.add(Ability.Property(Ability.Property.Type.BONUS_FIRE_RATE, it.toScaledValue())) }
    bulletResist?.let { properties.add(Ability.Property(Ability.Property.Type.BULLET_RESIST, it.toScaledValue())) }
    techResist?.let { properties.add(Ability.Property(Ability.Property.Type.TECH_RESIST, it.toScaledValue())) }
    maxStacks?.let { properties.add(Ability.Property(Ability.Property.Type.MAX_STACKS, it.toDouble().toScaledValue())) }

    return properties
}

private fun ScaledValueDto.toDomain(): ScaledValue? {
    val value = value ?: return null

    val scale = scale?.let { scaleDto ->
        val scaleValue = scaleDto.value ?: return@let null
        val scaleType = scaleDto.type?.toScaleType() ?: return@let null

        ScaledValue.Scale(
            value = scaleValue,
            type = scaleType,
        )
    }

    val result = ScaledValue(
        value = value,
        scale = scale,
    )

    return result
}

private fun Double.toScaledValue(): ScaledValue {
    val result = ScaledValue(value = this, scale = null)
    return result
}

private fun String.toScaleType(): ScaledValue.ScaleType? {
    val result = when (this) {
        "spirit" -> ScaledValue.ScaleType.SPIRIT
        "weapon_damage" -> ScaledValue.ScaleType.WEAPON_DAMAGE
        "weapon_damage_increase" -> ScaledValue.ScaleType.WEAPON_DAMAGE_INCREASE
        "weapon_power" -> ScaledValue.ScaleType.WEAPON_POWER
        "duration" -> ScaledValue.ScaleType.DURATION
        "power_increase" -> ScaledValue.ScaleType.POWER_INCREASE
        "range" -> ScaledValue.ScaleType.RANGE
        "melee" -> ScaledValue.ScaleType.MELEE
        "heavy_melee" -> ScaledValue.ScaleType.HEAVY_MELEE
        "healing" -> ScaledValue.ScaleType.HEALING
        "cooldown" -> ScaledValue.ScaleType.COOLDOWN
        "damage" -> ScaledValue.ScaleType.DAMAGE
        "stats_count" -> ScaledValue.ScaleType.STATS_COUNT
        "parry_cd" -> ScaledValue.ScaleType.PARRY_CD
        else -> null
    }

    return result
}
