package shah.nishant.findingfalcone.game.data

import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import dagger.Lazy
import kotlinx.coroutines.runBlocking
import org.junit.Test
import retrofit2.Response
import shah.nishant.findingfalcone.game.model.*

class GameRemoteDataSourceTest {

    private val api = mock<GameApi>()
    private val gameRemoteDataSource = GameRemoteDataSource(Lazy { api })

    @Test
    fun `should return the value returned by the getPlanets method of api `() {
        runBlocking {
            //given
            val planets = listOf(
                Planet("A", 100),
                Planet("B", 200),
                Planet("C", 300)
            )
            whenever(api.getPlanets()).thenReturn(planets)

            //when
            val result = gameRemoteDataSource.getPlanets()

            //then
            assertThat(result).isEqualTo(planets)
        }
    }

    @Test
    fun `should return the value returned by the getVehicles method of api `() {
        runBlocking {
            //given
            val vehicles = listOf(
                Vehicle("A", 1, 100, 2),
                Vehicle("B", 2, 200, 3),
                Vehicle("C", 3, 300, 4)
            )
            whenever(api.getVehicles()).thenReturn(vehicles)

            //when
            val result = gameRemoteDataSource.getVehicles()

            //then
            assertThat(result).isEqualTo(vehicles)
        }
    }

    @Test
    fun `should return the value returned by the getToken method of api `() {
        runBlocking {
            //given
            val token = TokenResponse("token")
            whenever(api.getToken()).thenReturn(token)

            //when
            val result = gameRemoteDataSource.getToken()

            //then
            assertThat(result).isEqualTo(token)
        }
    }

    @Test
    fun `should return the value returned by the findFalcone method of api `() {
        runBlocking {
            //given
            val planets = listOf("Planet A", "Planet B", "Planet C")
            val vehicles = listOf("Vehicle A", "Vehicle B", "Vehicle C")
            val tokenResponse = TokenResponse("token")
            val findRequest = FindRequest("token", planets, vehicles)
            val findResponse = Response.success<FindResponse>(
                FindResponse(
                    "success",
                    "earth",
                    null
                )
            )
            whenever(api.findFalcone(findRequest)).thenReturn(findResponse)

            //when
            val result = gameRemoteDataSource.findFalcone(findRequest)

            //then
            verify(api).findFalcone(findRequest)
            assertThat(result).isEqualTo(findResponse)
        }
    }
}
