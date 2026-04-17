package io.github.sophon.core.domain.model

data class Ability(
    val key: String,
    val heroName: String,
    val name: String,
    val description: String,
    val imageUrl: String?,

    val upgradeList: List<Map<String, Double>>,
    val propertyMap: Map<String, Property>,
    val bonusSet: Set<Property>,
)
