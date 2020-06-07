package shah.nishant.findingfalcone.game.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Target(val planet: Planet, val vehicle: Vehicle? = null) : Parcelable
