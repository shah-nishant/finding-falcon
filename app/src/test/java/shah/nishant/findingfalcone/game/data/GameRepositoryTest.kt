package shah.nishant.findingfalcone.game.data

import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import dagger.Lazy
import kotlinx.coroutines.runBlocking
import org.junit.Test
import retrofit2.Response
import shah.nishant.findingfalcone.game.model.*

class GameRepositoryTest {

    private val remoteDataSource = mock<GameRemoteDataSource>()
    private val repository =
        GameRepository(Lazy { remoteDataSource }, Lazy { TestDispatcherProvider() })

    @Test
    fun `should return the value returned by the getPlanets method of data source`() {
        runBlocking {
            //given
            val planets = listOf(
                Planet("A", 100),
                Planet("B", 200),
                Planet("C", 300)
            )
            whenever(remoteDataSource.getPlanets()).thenReturn(planets)

            //when
            val result = repository.getPlanets()

            //then
            assertThat(result).isEqualTo(planets)
        }
    }

    @Test
    fun `should return the value returned by the getVehicles method of data source`() {
        runBlocking {
            //given
            val vehicles = listOf(
                Vehicle("A", 1, 100, 2),
                Vehicle("B", 2, 200, 3),
                Vehicle("C", 3, 300, 4)
            )
            whenever(remoteDataSource.getVehicles()).thenReturn(vehicles)

            //when
            val result = repository.getVehicle()

            //then
            assertThat(result).isEqualTo(vehicles)
        }
    }

    @Test
    fun `should get token and make a request find falcone adn return the response`() {
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

            whenever(remoteDataSource.getToken()).thenReturn(tokenResponse)
            whenever(remoteDataSource.findFalcone(findRequest)).thenReturn(findResponse)

            //when
            val result = repository.findFalcone(planets, vehicles)

            //then
            verify(remoteDataSource, times(1)).getToken()
            verify(remoteDataSource, times(1)).findFalcone(findRequest)
            assertThat(result).isEqualTo(findResponse)
        }
    }

}