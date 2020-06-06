package shah.nishant.findingfalcon.game.model

import android.os.Parcelable
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Keep
@Parcelize
data class Vehicle(
    val name: String?,
    @SerializedName("total_no")
    val count: Long?,
    @SerializedName("max_distance")
    val distance: Long?,
    val speed: Long?
) : Parcelable
