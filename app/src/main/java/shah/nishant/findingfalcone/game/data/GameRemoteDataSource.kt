package shah.nishant.findingfalcone.game.data

import dagger.Lazy
import shah.nishant.findingfalcone.game.model.FindRequest
import javax.inject.Inject

class GameRemoteDataSource @Inject constructor(
    private val gameApi: Lazy<GameApi>
) {

    suspend fun getPlanets() = gameApi.get().getPlanets()

    suspend fun getVehicles() = gameApi.get().getVehicles()

    suspend fun getToken() = gameApi.get().getToken()

    suspend fun findFalcone(findRequest: FindRequest)  = gameApi.get().findFalcone(findRequest)

}
