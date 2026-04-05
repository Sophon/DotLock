package io.github.sophon.firefrog.core.domain.model

sealed class StatUpgrade {
    abstract val value: ScaledValue

    data class Damage(override val value: ScaledValue) : StatUpgrade()
    data class Dps(override val value: ScaledValue) : StatUpgrade()
    data class CombatBarrier(override val value: ScaledValue) : StatUpgrade()
    data class TotalHealthRegen(override val value: ScaledValue) : StatUpgrade()
    data class HealPerStack(override val value: ScaledValue) : StatUpgrade()
    data class VexBarrierCombatBarrier(override val value: ScaledValue) : StatUpgrade()
    data class LifestrikeHeal(override val value: ScaledValue) : StatUpgrade()
    data class SpiritDamage(override val value: ScaledValue) : StatUpgrade()
    data class ImpactDamage(override val value: ScaledValue) : StatUpgrade()
    data class LifestealHeal(override val value: ScaledValue) : StatUpgrade()
    data class LifestealHealPercent(override val value: ScaledValue) : StatUpgrade()
    data class HeadShotBonusDamage(override val value: ScaledValue) : StatUpgrade()
    data class HealPercentPerHeadshot(override val value: ScaledValue) : StatUpgrade()
    data class ProcBaseAttackDamagePercent(override val value: ScaledValue) : StatUpgrade()
    data class ProcBaseAttackDamagePercentAltFire(override val value: ScaledValue) : StatUpgrade()
    data class BonusHealth(override val value: ScaledValue) : StatUpgrade()
    data class BaseAttackDamagePercent(override val value: ScaledValue) : StatUpgrade()
    data class DotHealthPercent(override val value: ScaledValue) : StatUpgrade()
    data class DamagePulseAmount(override val value: ScaledValue) : StatUpgrade()
    data class DpsIncrease(override val value: ScaledValue) : StatUpgrade()
    data class DpsMax(override val value: ScaledValue) : StatUpgrade()
    data class BulletArmorReduction(override val value: ScaledValue) : StatUpgrade()
    data class HealingPerCast(override val value: ScaledValue) : StatUpgrade()
    data class Regeneration(override val value: ScaledValue) : StatUpgrade()
    data class HealOnVeil(override val value: ScaledValue) : StatUpgrade()
    data class BonusPerChain(override val value: ScaledValue) : StatUpgrade()
    data class DamagePerChain(override val value: ScaledValue) : StatUpgrade()
}
