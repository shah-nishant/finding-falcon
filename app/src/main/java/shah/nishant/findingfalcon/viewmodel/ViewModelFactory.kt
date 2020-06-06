package shah.nishant.findingfalcon.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject
import javax.inject.Provider

class ViewModelFactory @Inject constructor(private val viewModelMap: Map<Class<out ViewModel>, Provider<ViewModel>>) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        var creator: Provider<out ViewModel>? = viewModelMap[modelClass]
        if (creator == null) {
            for ((key, value) in viewModelMap) {
                if (modelClass.isAssignableFrom(key)) {
                    creator = value
                    break
                }
            }
        }
        requireNotNull(creator) { "unknown model class $modelClass" }
        return try {
            creator.get() as T
        } catch (ignored: RuntimeException) {
            throw IllegalArgumentException("unknown model class $modelClass")
        }
    }

}
