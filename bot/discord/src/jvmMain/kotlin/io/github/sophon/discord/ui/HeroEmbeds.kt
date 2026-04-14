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
    hero.imageUrl?.let { heroUrl ->
        thumbnail { url = heroUrl }
    }

    weaponSection(hero.weapon)
    vitalitySection(hero)
    abilitySection(hero.boundAbilities)
}


private fun EmbedBuilder.weaponSection(weapon: Weapon) {
    mandatoryField(
        name = "Bullets",
        value = buildString {
            append("- DMG: ${weapon.bulletDamage}\n")
            append("- SPD: ${weapon.bulletSpeed}\n")
            append("- pSHT: ${weapon.bulletsPerShot}; pBRST: ${weapon.bulletsPerBurst}\n")
        },
    )
    mandatoryField(
        name = "Clip",
        value = buildString {
            append("- Size: ${weapon.clipSize}\n")
            append("- Reload: ${weapon.reloadTime}\n")
        }
    )
}

private fun EmbedBuilder.vitalitySection(hero: Hero) {
    mandatoryField(
        name = "HP",
        value = buildString {
            append("- HP: ${hero.maxHealth}\n")
            append("- regen: ${hero.baseHealthRegen}\n")
            append("- Stamina: ${hero.stamina.stamina}\n")
            append("- Stamina CD: ${hero.stamina.cooldown}\n")
        }
    )
    mandatoryField(
        name = "Movement",
        value = buildString {
            append("- Move: ${hero.movement.maxMoveSpeed}\n")
            append("- Sprint: ${hero.movement.sprintSpeedMultiplier}\n")
            append("- Dash: ${hero.dash.groundDashSpeed} (gnd); ${hero.dash.airDashSpeed} (air)\n")
        }
    )
}

private fun EmbedBuilder.abilitySection(abilityList: List<Hero.BoundAbility>) {
    mandatoryField(
        name = "Abilities",
        value = buildString {
            for (ability in abilityList) {
                append("${ability.slot}. ${ability.name}\n")
            }
        },
        inline = false,
    )
}


private const val BEIGE = 0x00EEDCBD