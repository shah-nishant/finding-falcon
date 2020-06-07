package shah.nishant.findingfalcone.game.viewmodel

import androidx.lifecycle.*
import dagger.Lazy
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import shah.nishant.findingfalcone.game.data.GameRepository
import shah.nishant.findingfalcone.game.model.*
import shah.nishant.findingfalcone.game.model.Target
import javax.inject.Inject

class GameViewModel @Inject constructor(
    private val gameRepository: Lazy<GameRepository>
) : ViewModel() {

    private val _gameMetaData = MutableLiveData<GameMetaData>()
    val gameMetaData: LiveData<GameMetaData> = _gameMetaData

    private val _findResponse = MutableLiveData<FindResponse>()
    val findResponse: LiveData<FindResponse> = _findResponse

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
        val indexForPlanet = updatedTargets.indexOfFirst {
            it.planet == planet
        }
        updatedTargets[indexForPlanet] = Target(planet, null)
        _gameMetaData.value = _gameMetaData.value!!.copy(targets = updatedTargets)
    }

    fun isSelectionComplete(): Boolean {
        return gameMetaData.value!!.targets.filter {
            it.vehicle != null
        }.size == 4
    }

    fun findFalcone() {
        viewModelScope.launch {
            val selectedTargets = gameMetaData.value!!.targets.filter {
                it.vehicle != null
            }
            val planetNames = selectedTargets.map { it.planet.name!! }
            val vehicleNames = selectedTargets.map { it.vehicle!!.name!! }
            _findResponse.postValue(gameRepository.get().findFalcone(planetNames, vehicleNames))
        }
    }

    fun getVehiclesDto(planet: Planet): List<VehicleDto> {
        return gameMetaData.value!!.vehicles.map { vehicle ->
            val used = gameMetaData.value!!.targets.filter { it.vehicle == vehicle }.size
            val available = vehicle.count!! - used
            VehicleDto(vehicle, available, planet.distance!! <= vehicle.distance!!)
        }
    }

}
