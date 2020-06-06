package shah.nishant.findingfalcon.game.model

import androidx.annotation.Keep

@Keep
data class Vehicle(
    val name: String,
    val count: Long,
    val distance: Long,
    val speed: Long
)