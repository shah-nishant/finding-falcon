package shah.nishant.findingfalcone.extensions

import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController

fun Fragment.navigate(directions: NavDirections) {
    findNavController().navigate(directions)
}

fun Fragment.showShortToast(@StringRes resId: Int) = context?.showShortToast(resId)
