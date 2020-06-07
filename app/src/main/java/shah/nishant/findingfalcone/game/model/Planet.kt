package shah.nishant.findingfalcone.game.model

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.android.parcel.Parcelize

@Keep
@Parcelize
data class Planet(val name: String?, val distance: String?) : Parcelable
