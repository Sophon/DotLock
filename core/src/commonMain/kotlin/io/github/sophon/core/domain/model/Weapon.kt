package io.github.sophon.core.domain.model

data class Weapon(
    val nameKey: String,
    val descriptionKey: String,
    val attributes: List<Attribute>,
    val bulletDamage: Double,
    val bulletSpeed: Double,
    val roundsPerSecond: Double,
    val clipSize: Int,
    val bulletsPerShot: Int,
    val bulletsPerBurst: Int,
    val burstInterShotInterval: Double,
    val reloadTime: Double,
    val reloadDelay: Double,
    val isSingleReload: Boolean,
    val reloadMoveSpeedMultiplier: Double,
    val shootMoveSpeedMultiplier: Double,
    val bulletGravityScale: Double,
    val canCrit: Boolean,
    val hitsOnceAcrossAllBullets: Boolean,
    val ammoConsumedPerShot: Int,
    val falloff: FalloffProfile,
    val damageRange: DamageRange,
) {
    enum class Attribute {
        BURST_FIRE,
        MEDIUM_RANGE,
        CLOSE_RANGE,
        HEAVY_HITTER,
        SPREADSHOT,
        BEAM_WEAPON,
        LONG_RANGE,

        UNKNOWN,
    }

    data class FalloffProfile(
        val startRange: Double,
        val endRange: Double,
        val startScale: Double,
        val endScale: Double,
        val bias: Double
    )

    data class DamageRange(
        val dps: Double,
        val sustainedDps: Double,
    )
}
