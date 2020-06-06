package shah.nishant.findingfalcon.game.data

import dagger.Lazy
import javax.inject.Inject

class GameRemoteDataSource @Inject constructor(
    private val gameApi: Lazy<GameApi>
) {

    suspend fun getPlanets() = gameApi.get().getPlanets()

    suspend fun getVehicles() = gameApi.get().getVehicles()

}
