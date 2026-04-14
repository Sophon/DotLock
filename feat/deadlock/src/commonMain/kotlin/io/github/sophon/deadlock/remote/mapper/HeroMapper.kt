package io.github.sophon.deadlock.remote.mapper

import io.github.sophon.core.domain.model.Hero
import io.github.sophon.core.domain.model.Weapon
import io.github.sophon.core.util.formKey
import io.github.sophon.deadlock.remote.dto.HeroDto

internal fun Map.Entry<String, HeroDto>.toDomain(imageUrls: Map<String, String>): Hero {
    val dto = value
    val key = key
    val heroKey = (value.name?.formKey() ?: key)

    val hero = Hero(
        key = heroKey,
        name = dto.name ?: key,
        imageUrl = imageUrls[heroKey],

        type = dto.type?.toHeroType(),
        loreKey = dto.lore,
        playstyleKey = dto.playstyle,
        roleKey = dto.role,

        isInDevelopment = dto.inDevelopment ?: false,
        isInHeroLabs = dto.inHeroLabs ?: false,
        isSelectable = dto.isSelectable ?: true,
        isRecommended = dto.isRecommended ?: false,

        maxHealth = dto.maxHealth?.toDouble() ?: 0.0,
        baseHealthRegen = dto.baseHealthRegen ?: 0.0,

        movement = dto.toDomainMovement(),
        dash = dto.toDomainDash(),
        stamina = dto.toDomainStamina(),

        melee = dto.toDomainMelee(),

        levelScaling = dto.levelScaling.toDomain(),

        critDamageBonusPercent = dto.critDamageBonusPercent?.toDouble() ?: 0.0,
        critDamageReceivedPercent = dto.critDamageReceivedPercent?.toDouble() ?: 0.0,
        procBuildUpRateScale = dto.procBuildUpRateScale ?: 0.0,
        weaponPowerScale = dto.weaponPowerScale ?: 0.0,
        bulletLifestealEffectiveness = dto.heroBulletLifestealEffectiveness ?: 0.0,
        spiritLifestealEffectiveness = dto.heroSpiritLifestealEffectiveness ?: 0.0,
        techDuration = dto.techDuration ?: 0.0,
        techRange = dto.techRange ?: 0.0,

        boundAbilities = dto.boundAbilities?.toBoundAbilities() ?: emptyList(),
        weapon = dto.weapon.toDomain(),
    )

    return hero
}

private fun String.toHeroType(): Hero.Type? {
    val type = when (this) {
        "Brawler" -> Hero.Type.BRAWLER
        "Marksman" -> Hero.Type.MARKSMAN
        "Tank" -> Hero.Type.TANK
        "Support" -> Hero.Type.SUPPORT
        "Assassin" -> Hero.Type.ASSASSIN
        "Mystic" -> Hero.Type.MYSTIC
        else -> null
    }
    return type
}

private fun HeroDto.toDomainMovement(): Hero.MovementStats {
    val movement = Hero.MovementStats(
        maxMoveSpeed = maxMoveSpeed ?: 0.0,
        crouchSpeed = crouchSpeed ?: 0.0,
        sprintSpeedMultiplier = sprintSpeed ?: 0.0,
        moveAcceleration = moveAcceleration ?: 0.0,
    )
    return movement
}

private fun HeroDto.toDomainDash(): Hero.DashStats {
    val dash = Hero.DashStats(
        airDashDistanceMeters = airDashDistanceInMeters ?: 0.0,
        airDashDuration = airDashDuration ?: 0.0,
        airDashSpeed = airDashSpeed ?: 0.0,
        groundDashDistanceMeters = groundDashDistanceInMeters ?: 0.0,
        groundDashDuration = groundDashDuration ?: 0.0,
        groundDashSpeed = groundDashSpeed ?: 0.0,
    )
    return dash
}

private fun HeroDto.toDomainStamina(): Hero.StaminaStats {
    val stamina = Hero.StaminaStats(
        stamina = stamina ?: 0,
        cooldown = staminaCooldown ?: 0.0,
        regenPerSecond = staminaRegenPerSecond ?: 0.0,
    )
    return stamina
}

private fun HeroDto.toDomainMelee(): Hero.MeleeStats {
    val melee = Hero.MeleeStats(
        lightDamage = lightMeleeDamage ?: 0.0,
        heavyDamage = heavyMeleeDamage ?: 0.0,
    )
    return melee
}

private fun HeroDto.LevelScaling?.toDomain(): Hero.LevelScaling {
    val scaling = Hero.LevelScaling(
        bulletDamage = this?.bulletDamage ?: 0.0,
        maxHealth = this?.maxHealth ?: 0.0,
        techPower = this?.techPower ?: 0.0,
        lightMeleeDamage = this?.lightMeleeDamage ?: 0.0,
        heavyMeleeDamage = this?.heavyMeleeDamage ?: 0.0,
        powerIncreases = this?.powerIncreases ?: 0.0,
        dps = this?.dps,
        sustainedDps = this?.sustainedDps,
        bonusAttackRange = this?.bonusAttackRange,
        bulletResist = this?.bulletResist,
    )
    return scaling
}

private fun Map<String, HeroDto.BoundAbility>.toBoundAbilities(): List<Hero.BoundAbility> {
    val result = entries.mapNotNull { (slot, ability) ->
        val slotInt = slot.toIntOrNull() ?: return@mapNotNull null
        val name = ability.name ?: return@mapNotNull null
        val key = ability.key ?: return@mapNotNull null

        Hero.BoundAbility(
            slot = slotInt,
            name = name,
            key = key,
        )
    }.sortedBy { it.slot }

    return result
}

private fun HeroDto.Weapon?.toDomain(): Weapon {
    val result = Weapon(
        nameKey = this?.nameKey.orEmpty(),
        descriptionKey = this?.descKey.orEmpty(),
        attributes = this?.weaponTypes?.mapNotNull { it.toWeaponAttribute() } ?: emptyList(),
        bulletDamage = this?.bulletDamage ?: 0.0,
        bulletSpeed = this?.bulletSpeed ?: 0.0,
        roundsPerSecond = this?.roundsPerSecond ?: 0.0,
        clipSize = this?.clipSize ?: 0,
        bulletsPerShot = this?.bulletsPerShot ?: 0,
        bulletsPerBurst = this?.bulletsPerBurst ?: 0,
        burstInterShotInterval = this?.burstInterShotInterval  ?: 0.0,
        reloadTime = this?.reloadTime ?: 0.0,
        reloadDelay = this?.reloadDelay ?: 0.0,
        isSingleReload = this?.reloadSingle ?: false,
        reloadMoveSpeedMultiplier = this?.reloadMovespeed ?: 0.0,
        shootMoveSpeedMultiplier = this?.shootMoveSpeed ?: 0.0,
        bulletGravityScale = this?.bulletGravityScale ?: 0.0,
        canCrit = this?.canCrit ?: false,
        hitsOnceAcrossAllBullets = this?.hitOnceAcrossAllBullets ?: false,
        ammoConsumedPerShot = this?.ammoConsumedPerShot ?: 0,
        falloff = Weapon.FalloffProfile(
            startRange = this?.falloffStartRange ?: 0.0,
            endRange = this?.falloffEndRange ?: 0.0,
            startScale = this?.falloffStartScale ?: 0.0,
            endScale = this?.falloffEndScale ?: 0.0,
            bias = this?.falloffBias ?: 0.0,
        ),
        damageRange = Weapon.DamageRange(
            dps = this?.dps ?: 0.0,
            sustainedDps = this?.sustainedDps ?: 0.0,
        ),
    )

    return result
}

private fun String.toWeaponAttribute(): Weapon.Attribute? {
    val result = when (this) {
        "Attribute_EWeaponAttribute_BurstFire" -> Weapon.Attribute.BURST_FIRE
        "Attribute_EWeaponAttribute_MediumRange" -> Weapon.Attribute.MEDIUM_RANGE
        "Attribute_EWeaponAttribute_CloseRange" -> Weapon.Attribute.CLOSE_RANGE
        "Attribute_EWeaponAttribute_HeavyHitter" -> Weapon.Attribute.HEAVY_HITTER
        "Attribute_EWeaponAttribute_Spreadshot" -> Weapon.Attribute.SPREADSHOT
        "Attribute_EWeaponAttribute_BeamWeapon" -> Weapon.Attribute.BEAM_WEAPON
        "Attribute_EWeaponAttribute_LongRange" -> Weapon.Attribute.LONG_RANGE
        else -> null
    }

    return result
}