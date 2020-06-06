package shah.nishant.findingfalcon.game.data

import retrofit2.http.GET
import shah.nishant.findingfalcon.game.model.Planet
import shah.nishant.findingfalcon.game.model.Vehicle

interface GameApi {

    @GET("planets")
    suspend fun getPlanets(): List<Planet>

    @GET("vehicles")
    suspend fun getVehicles(): List<Vehicle>
}
