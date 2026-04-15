package io.github.sophon.core.domain.model

data class Item(
    val key: String,
    val altKey: String,
    val name: String,
    val imageUrl: String?,

    val description: String?,
    val isStreetBrawl: Boolean,

    val shop: ShopInfo,
    val timing: Timing?,
    val targeting: Targeting?,

    val bonusSet: Set<Bonus>,
) {
    data class Timing(
        val cooldown: ScaledValue?,
        val cooldownBetweenCharge: ScaledValue?,
        val castDelay: ScaledValue?,
        val postCastDuration: ScaledValue?,
        val channelTime: ScaledValue?,
        val channelMoveSpeed: ScaledValue?,
        val duration: ScaledValue?,
        val charges: ScaledValue?,
    )

    data class Targeting(
        val castRange: ScaledValue?,
        val unitTargetLimit: ScaledValue?,
    )
}
