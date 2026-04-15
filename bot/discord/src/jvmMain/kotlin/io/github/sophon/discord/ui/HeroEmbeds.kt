package io.github.sophon.discord.ui

import dev.kord.common.Color
import dev.kord.rest.builder.message.EmbedBuilder
import io.github.sophon.core.domain.model.FeatureInfo
import io.github.sophon.core.domain.model.Hero
import io.github.sophon.core.domain.model.Weapon

internal fun heroEmbed(
    hero: Hero,
    featureInfo: FeatureInfo,
): EmbedBuilder.() -> Unit = {
    title = hero.name
    color = Color(BEIGE)
    featureFooter(featureInfo)
    hero.url.image?.let { heroUrl ->
        thumbnail { url = heroUrl }
    }
    hero.url.wiki?.let { wikiUrl -> url = wikiUrl }

    vitalitySection(hero)
    weaponSection(hero.weapon, hero.melee)
    abilitySection(hero.boundAbilities)
}


private fun EmbedBuilder.vitalitySection(hero: Hero) {
    mandatoryField(
        name = "**General**",
        value = buildString {
            append("- ${Emoji.HEALTH} HP: ${hero.maxHealth}\n")
            append("- ${Emoji.HP_REGEN} RGN: ${hero.baseHealthRegen}\n")
            append("- ${Emoji.SPRINT_SPEED} STM: ${hero.stamina.stamina}\n")
            append("- ${Emoji.MOVE_SPEED} MS: ${hero.movement.maxMoveSpeed}\n")
            append("- ${Emoji.SPRINT_SPEED} SPR: ${hero.movement.sprintSpeedMultiplier}\n")
        }
    )
}

private fun EmbedBuilder.weaponSection(weapon: Weapon, meleeStats: Hero.MeleeStats) {
    mandatoryField(
        name = "**Weapon**",
        value = buildString {
            append("- ${Emoji.CLIP_SIZE} CLIP: ${weapon.clipSize}\n")
            append("- ${Emoji.BULLET_SPEED} SPD: ${weapon.bulletSpeed}\n")
            append("- ${Emoji.BULLETS} SHT/BRST: ${weapon.bulletsPerShot}/${weapon.bulletsPerBurst}\n")
            append("- ${Emoji.RELOAD_TIME} RLD: ${weapon.reloadTime}\n")
            append(" - ${Emoji.MELEE} L/H: ${meleeStats.lightDamage}/${meleeStats.heavyDamage}")
        },
    )
}

private fun EmbedBuilder.abilitySection(abilityList: List<Hero.BoundAbility>) {
    mandatoryField(
        name = "**ABILITIES**",
        value = buildString {
            for (ability in abilityList) {
                append("${ability.slot}. **${ability.name}**\n")
            }
        },
        inline = false,
    )
}


private const val BEIGE = 0x00EEDCBD