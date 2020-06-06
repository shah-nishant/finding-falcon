package shah.nishant.findingfalcon.game.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class Vehicle(
    val name: String?,
    @SerializedName("total_no")
    val count: Long?,
    @SerializedName("max_distance")
    val distance: Long?,
    val speed: Long?
)
