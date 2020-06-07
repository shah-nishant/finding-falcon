package shah.nishant.findingfalcone.game.model

import com.google.gson.annotations.SerializedName

data class FindResponse(
    val status: String?,
    @SerializedName("planet_name")
    val planeName: String?,
    val error: String?
) {
    fun isSuccessful() = status == "success" && planeName.isNullOrBlank()
}
