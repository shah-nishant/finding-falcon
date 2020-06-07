package shah.nishant.findingfalcone.game.data

import dagger.Lazy
import kotlinx.coroutines.withContext
import shah.nishant.findingfalcone.coroutines.DispatcherProvider
import javax.inject.Inject

class GameRepository @Inject constructor(
    private val remoteDataSource: Lazy<GameRemoteDataSource>,
    private val dispatcherProvider: Lazy<DispatcherProvider>
) {

    suspend fun getGetPlanets() = withContext(dispatcherProvider.get().io()) {
        remoteDataSource.get().getPlanets()
    }

    suspend fun getGetVehicle() = withContext(dispatcherProvider.get().io()) {
        remoteDataSource.get().getVehicles()
    }

    fun findFalcone() {

    }

}