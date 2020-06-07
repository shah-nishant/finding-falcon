package shah.nishant.findingfalcon.game.viewmodel

import androidx.lifecycle.*
import dagger.Lazy
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import shah.nishant.findingfalcon.game.data.GameRepository
import shah.nishant.findingfalcon.game.model.GameMetaData
import shah.nishant.findingfalcon.game.model.Planet
import shah.nishant.findingfalcon.game.model.Target
import shah.nishant.findingfalcon.game.model.Vehicle
import javax.inject.Inject

class GameViewModel @Inject constructor(
    private val gameRepository: Lazy<GameRepository>
) : ViewModel() {

    private val _gameMetaData = MutableLiveData<GameMetaData>()
    val gameMetaData: LiveData<GameMetaData> = _gameMetaData

    val vehicles = Transformations.map(gameMetaData) {
        it.vehicles
    }

    fun init() {
        viewModelScope.launch {
            val planets = async { gameRepository.get().getGetPlanets() }
            val vehicles = async { gameRepository.get().getGetVehicle() }

            // Run APIs in parallel
            val gameMetaData = GameMetaData(
                planets.await().map { Target(it) },
                vehicles.await()
            )
            _gameMetaData.postValue(gameMetaData)
        }
    }

    fun onVehicleSelected(planet: Planet, vehicle: Vehicle) {
        val updatedTargets = gameMetaData.value!!.targets.toMutableList()
        val indexForPlanet = updatedTargets.indexOfFirst {
            it.planet == planet
        }
        if (indexForPlanet == -1) {
            updatedTargets.add(Target(planet, vehicle))
        } else {
            updatedTargets[indexForPlanet] = Target(planet, vehicle)
        }
        _gameMetaData.value = _gameMetaData.value!!.copy(targets = updatedTargets)
    }

    fun onVehicleRemoved(planet: Planet) {
        val updatedTargets = gameMetaData.value!!.targets.toMutableList()
        updatedTargets.removeAll {
            it.planet == planet
        }
        _gameMetaData.value = _gameMetaData.value!!.copy(targets = updatedTargets)
    }

    fun isSelectionComplete(): Boolean {
        return gameMetaData.value!!.targets.filter {
            it.vehicle != null
        }.size == 4
    }

}
