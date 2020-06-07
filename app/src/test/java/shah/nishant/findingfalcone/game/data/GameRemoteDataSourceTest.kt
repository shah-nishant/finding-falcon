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
            val list = listOf<Planet>(mock(), mock(), mock())
            whenever(api.getPlanets()).thenReturn(list)

            //when
            val result = gameRemoteDataSource.getPlanets()

            //then
            assertThat(result).isEqualTo(list)
        }
    }

    @Test
    fun `should return the value returned by the getVehicles method of api `() {
        runBlocking {
            //given
            val list = listOf<Vehicle>(mock(), mock(), mock())
            whenever(api.getVehicles()).thenReturn(list)

            //when
            val result = gameRemoteDataSource.getVehicles()

            //then
            assertThat(result).isEqualTo(list)
        }
    }

    @Test
    fun `should return the value returned by the getToken method of api `() {
        runBlocking {
            //given
            val token = mock<TokenResponse>()
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
            val request = mock<FindRequest>()
            val response = mock<Response<FindResponse>>()
            whenever(api.findFalcone(request)).thenReturn(response)

            //when
            val result = gameRemoteDataSource.findFalcone(request)

            //then
            verify(api).findFalcone(request)
            assertThat(result).isEqualTo(response)
        }
    }
}
