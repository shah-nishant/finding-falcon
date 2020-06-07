package shah.nishant.findingfalcone.game.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.Lazy
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import shah.nishant.findingfalcone.coroutines.Result
import shah.nishant.findingfalcone.coroutines.toResult
import shah.nishant.findingfalcone.game.data.GameRepository
import shah.nishant.findingfalcone.game.model.*
import shah.nishant.findingfalcone.game.model.Target
import javax.inject.Inject

class GameViewModel @Inject constructor(
    private val gameRepository: Lazy<GameRepository>
) : ViewModel() {

    val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        println("Caught $exception")
    }

    private val _gameMetaData = MutableLiveData<GameMetaData>()
    val gameMetaData: LiveData<GameMetaData> = _gameMetaData

    private val _loadError = MutableLiveData<Boolean>()
    val loadError: LiveData<Boolean> = _loadError

    private val _findResponse = MutableLiveData<Result<FindResponse>>()
    val findResponse: LiveData<Result<FindResponse>> = _findResponse

    fun init() {
        viewModelScope.launch(exceptionHandler) {
            val planets = async { gameRepository.get().getPlanets() }
            val vehicles = async { gameRepository.get().getVehicle() }

            // Run APIs in parallel
            try {
                val gameMetaData = GameMetaData(
                    planets.await().map { Target(it) },
                    vehicles.await()
                )
                _loadError.postValue(false)
                _gameMetaData.postValue(gameMetaData)
            } catch (t: Throwable) {
                if (t !is CancellationException) {
                    _loadError.postValue(true)
                }
            }

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
        viewModelScope.launch(exceptionHandler) {
            val selectedTargets = gameMetaData.value!!.targets.filter {
                it.vehicle != null
            }
            val planetNames = selectedTargets.map { it.planet.name!! }
            val vehicleNames = selectedTargets.map { it.vehicle!!.name!! }
            try {
                val response = gameRepository.get().findFalcone(planetNames, vehicleNames)
                if (response.isSuccessful && response.body() != null) {
                    _findResponse.postValue(Result.Success(response.body()!!))
                } else {
                    _findResponse.postValue(Result.Failure(response.errorBody()?.string()))
                }
            } catch (t: Throwable) {
                if (t !is CancellationException) {
                    _findResponse.value = t.toResult()
                }
            }
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
