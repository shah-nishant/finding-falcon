package shah.nishant.findingfalcone.game.data

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import shah.nishant.findingfalcone.game.model.*

interface GameApi {

    @GET("planets")
    suspend fun getPlanets(): List<Planet>

    @GET("vehicles")
    suspend fun getVehicles(): List<Vehicle>

    @Headers("Accept: application/json")
    @POST("token")
    suspend fun getToken(): TokenResponse

    @Headers("Accept: application/json")
    @POST("find")
    suspend fun findFalcone(@Body findRequest: FindRequest): FindResponse
}
