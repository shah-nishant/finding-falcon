package shah.nishant.findingfalcone.game.model

import com.google.gson.annotations.SerializedName

data class FindRequest(
    val token: String,
    @SerializedName("planet_names")
    val planetNames: List<String>,
    @SerializedName("vehicle_names")
    val vehicleNames: List<String>
)
