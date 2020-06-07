package shah.nishant.findingfalcone.game.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FindResponse(
    val status: String?,
    @SerializedName("planet_name")
    val planeName: String?,
    val error: String?
) : Parcelable {
    fun isSuccessful() = status == "success" && planeName.isNullOrBlank()
}
