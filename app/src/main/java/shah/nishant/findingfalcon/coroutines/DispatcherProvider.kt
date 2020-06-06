package shah.nishant.findingfalcon.coroutines

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class DispatcherProvider @Inject constructor() {

    fun main(): CoroutineDispatcher = Dispatchers.Main

    fun io(): CoroutineDispatcher = Dispatchers.IO

}
