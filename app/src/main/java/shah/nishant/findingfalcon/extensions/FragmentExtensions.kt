package shah.nishant.findingfalcon.extensions

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController

inline fun <reified T : ViewModel> Fragment.createViewModel(viewModelFactory: ViewModelProvider.Factory): T =
    ViewModelProvider(this, viewModelFactory)[T::class.java]

fun Fragment.navigate(directions: NavDirections) {
    findNavController().navigate(directions)
}
