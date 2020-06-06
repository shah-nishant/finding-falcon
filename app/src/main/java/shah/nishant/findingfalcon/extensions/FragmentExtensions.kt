package shah.nishant.findingfalcon.extensions

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

inline fun <reified T : ViewModel> Fragment.createViewModel(viewModelFactory: ViewModelProvider.Factory): T =
    ViewModelProvider(this, viewModelFactory)[T::class.java]