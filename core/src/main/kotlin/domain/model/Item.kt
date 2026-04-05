package domain.model

data class Item(
    val key: String,
    val name: String?,
    val description: String?,
    val isDisabled: Boolean,
    val isStreetBrawl: Boolean,

    val shop: ShopInfo,
    val timing: Timing?,
    val targeting: Targeting?,

    val statUpgradeSet: Set<StatUpgrade>,
    val propertyUpgradeSet: Set<PropertyUpgrade>,
) {
    data class Timing(
        val cooldown: Double?,
        val cooldownBetweenCharge: Double?,
        val castDelay: Double?,
        val postCastDuration: Double?,
        val channelTime: Double?,
        val channelMoveSpeed: Double?,
        val duration: Double?,
        val charges: Int?,
    )

    data class Targeting(
        val castRange: Double?,
        val unitTargetLimit: Int?,
    )
}