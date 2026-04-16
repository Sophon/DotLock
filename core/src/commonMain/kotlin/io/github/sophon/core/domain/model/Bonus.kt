package io.github.sophon.core.domain.model

data class Bonus(
    val type: ActivationType,
    val descKey: String? = null,
    val cooldown: Double? = null,
    val chargeUp: Double? = null,
    val properties: List<Property>,
)
