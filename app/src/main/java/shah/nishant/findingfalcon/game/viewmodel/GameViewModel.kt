package shah.nishant.findingfalcon.game.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import dagger.Lazy
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import shah.nishant.findingfalcon.game.data.GameRepository
import shah.nishant.findingfalcon.game.model.GameMetaData
import javax.inject.Inject

class GameViewModel @Inject constructor(private val gameRepository: Lazy<GameRepository>) :
    ViewModel() {

    private val _gameMetaData = MutableLiveData<GameMetaData>()
    val gameMetaData = _gameMetaData

    fun init() {
        viewModelScope.launch {
            val planets = async { gameRepository.get().getGetPlanets() }
            val vehicles = async { gameRepository.get().getGetVehicle() }

            // Run APIs in parallel
            val gameMetaData = GameMetaData(planets.await(), vehicles.await())
            _gameMetaData.postValue(gameMetaData)
        }
    }

    class Factory(private val gameRepository: Lazy<GameRepository>) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return GameViewModel(gameRepository) as T
        }
    }

}
