package io.github.sophon.core.domain.model

data class Ability(
    val key: String,
    val altKey: String, //data is shit, we need to do this
    val name: String,
    val imageUrl: String?,

    val property: Property,
    val bonusSet: Set<Bonus>,
) {
    data class Property(
        val channelTime: ScaledValue?,
        val chargeCount: ScaledValue?,
        val chargeCooldown: ScaledValue?,
        val cooldown: ScaledValue?,
        val castRange: ScaledValue?,
        val duration: ScaledValue?,
        val radius: ScaledValue?,
    )
}
