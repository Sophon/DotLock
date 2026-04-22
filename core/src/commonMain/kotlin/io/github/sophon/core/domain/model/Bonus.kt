package io.github.sophon.core.domain.model

data class Bonus(
    val type: ActivationType,
    val description: String? = null,
    val cooldown: Double? = null,
    val chargeUp: Double? = null,
    val effectList: List<Effect>,
)
