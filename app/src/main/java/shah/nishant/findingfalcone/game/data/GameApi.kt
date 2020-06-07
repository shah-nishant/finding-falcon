package shah.nishant.findingfalcone.game.data

import retrofit2.http.GET
import shah.nishant.findingfalcone.game.model.Planet
import shah.nishant.findingfalcone.game.model.Vehicle

interface GameApi {

    @GET("planets")
    suspend fun getPlanets(): List<Planet>

    @GET("vehicles")
    suspend fun getVehicles(): List<Vehicle>
}
