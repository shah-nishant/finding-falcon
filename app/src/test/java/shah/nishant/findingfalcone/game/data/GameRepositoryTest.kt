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
import shah.nishant.findingfalcone.coroutines.DispatcherProvider
import shah.nishant.findingfalcone.game.model.*

class GameRepositoryTest {

    private val remoteDataSource = mock<GameRemoteDataSource>()
    private val dispatcherProvider = mock<DispatcherProvider>()
    private val repository = GameRepository(Lazy { remoteDataSource }, Lazy { dispatcherProvider })

    @Test
    fun `should return the value returned by the getPlanets method of data source`() {
        runBlocking {
            //given
            val list = listOf<Planet>(mock(), mock(), mock())
            whenever(remoteDataSource.getPlanets()).thenReturn(list)

            //when
            val result = repository.getPlanets()

            //then
            assertThat(result).isEqualTo(list)
        }
    }

    @Test
    fun `should return the value returned by the getVehicles method of data source`() {
        runBlocking {
            //given
            val list = listOf<Vehicle>(mock(), mock(), mock())
            whenever(remoteDataSource.getVehicles()).thenReturn(list)

            //when
            val result = repository.getVehicle()

            //then
            assertThat(result).isEqualTo(list)
        }
    }

    @Test
    fun `should get token and make a request find falcone adn return the response`() {
        runBlocking {
            //given
            val planets = listOf<String>(mock(), mock(), mock())
            val vehicles = listOf<String>(mock(), mock(), mock())
            val tokenResponse = mock<TokenResponse>()
            whenever(remoteDataSource.getToken()).thenReturn(tokenResponse)

            val findRequest = FindRequest(mock(), planets, vehicles)
            val findResponse = mock<Response<FindResponse>>()
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