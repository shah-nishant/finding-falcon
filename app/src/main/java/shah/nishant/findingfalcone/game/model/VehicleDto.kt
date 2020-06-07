package shah.nishant.findingfalcone.game.model

data class VehicleDto(
    val vehicle: Vehicle,
    val availableNumber: Long,
    val canReachThePlanet: Boolean
) {

    fun isSelectable() = availableNumber > 0 && canReachThePlanet

}
