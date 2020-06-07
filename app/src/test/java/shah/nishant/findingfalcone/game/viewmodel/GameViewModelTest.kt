package shah.nishant.findingfalcone.game.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.github.testcoroutinesrule.TestCoroutineRule
import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import dagger.Lazy
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Response
import shah.nishant.findingfalcone.coroutines.Result
import shah.nishant.findingfalcone.game.data.GameRepository
import shah.nishant.findingfalcone.game.getOrAwaitValue
import shah.nishant.findingfalcone.game.model.*
import shah.nishant.findingfalcone.game.model.Target

class GameViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    private val earth = Planet("Earth", 100)
    private val jupiter = Planet("Jupiter", 200)
    private val mars = Planet("Mars", 300)
    private val saturn = Planet("Saturn", 400)
    private val uranus = Planet("Uranus", 500)
    private val venus = Planet("Venus", 600)

    private val starShip = Vehicle("Star Ship", 1, 400, 2)
    private val apollo = Vehicle("Apollo", 2, 500, 3)
    private val falcon = Vehicle("Falcon", 3, 600, 4)

    private val planets = listOf(
        earth,
        jupiter,
        mars,
        saturn,
        uranus,
        venus
    )
    private val vehicles = listOf(
        starShip,
        apollo,
        falcon
    )

    private val repository = mock<GameRepository>()

    @Before
    fun setup() {
        runBlocking {
            whenever(repository.getPlanets()).thenReturn(planets)
            whenever(repository.getVehicle()).thenReturn(vehicles)
        }
    }

    // Failure cases to be added
    @Test
    fun `should return load error false and game meta data when api calls are successful`() {
        runBlocking {
            //given
            val viewModel = GameViewModel(Lazy { repository })

            //when
            viewModel.init()

            //then
            val loadErrorResult = viewModel.loadError.getOrAwaitValue()
            assertThat(loadErrorResult).isFalse()

            val gameMetaDataResult = viewModel.gameMetaData.getOrAwaitValue()
            assertThat(gameMetaDataResult).isEqualTo(
                GameMetaData(
                    planets.map { p -> Target(p) },
                    vehicles
                )
            )
        }
    }

    @Test
    fun `should add the vehicle for the given planet target`() {
        runBlocking {
            //given
            val viewModel = GameViewModel(Lazy { repository })

            //when
            viewModel.init()
            viewModel.onVehicleSelected(earth, starShip)
            viewModel.onVehicleSelected(jupiter, apollo)
            viewModel.onVehicleSelected(mars, falcon)

            //then
            val targets = viewModel.gameMetaData.getOrAwaitValue().targets
            assertThat(targets).isEqualTo(
                listOf(
                    Target(earth, starShip),
                    Target(jupiter, apollo),
                    Target(mars, falcon),
                    Target(saturn, null),
                    Target(uranus, null),
                    Target(venus, null)
                )
            )
        }
    }

    @Test
    fun `should update the vehicle for the given planet target`() {
        runBlocking {
            //given
            val viewModel = GameViewModel(Lazy { repository })

            //when
            viewModel.init()
            viewModel.onVehicleSelected(earth, starShip)
            viewModel.onVehicleSelected(jupiter, apollo)
            viewModel.onVehicleSelected(mars, falcon)
            viewModel.onVehicleSelected(earth, falcon)
            viewModel.onVehicleSelected(jupiter, falcon)

            //then
            val targets = viewModel.gameMetaData.getOrAwaitValue().targets
            assertThat(targets).isEqualTo(
                listOf(
                    Target(earth, falcon),
                    Target(jupiter, falcon),
                    Target(mars, falcon),
                    Target(saturn, null),
                    Target(uranus, null),
                    Target(venus, null)
                )
            )
        }
    }

    @Test
    fun `should remove the vehicle for the given planet target`() {
        runBlocking {
            //given
            val viewModel = GameViewModel(Lazy { repository })

            //when
            viewModel.init()
            viewModel.onVehicleSelected(earth, starShip)
            viewModel.onVehicleSelected(jupiter, apollo)
            viewModel.onVehicleSelected(mars, falcon)
            viewModel.onVehicleRemoved(jupiter)

            //then
            val targets = viewModel.gameMetaData.getOrAwaitValue().targets
            assertThat(targets).isEqualTo(
                listOf(
                    Target(earth, starShip),
                    Target(jupiter, null),
                    Target(mars, falcon),
                    Target(saturn, null),
                    Target(uranus, null),
                    Target(venus, null)
                )
            )
        }
    }

    @Test
    fun `should return true when vehicles are chosen for 4 planets`() {
        runBlocking {
            //given
            val viewModel = GameViewModel(Lazy { repository })

            //when
            viewModel.init()
            viewModel.onVehicleSelected(earth, starShip)
            viewModel.onVehicleSelected(jupiter, apollo)
            viewModel.onVehicleSelected(mars, apollo)
            viewModel.onVehicleSelected(saturn, falcon)
            val result = viewModel.isSelectionComplete()

            //then
            assertThat(result).isTrue()
        }
    }

    @Test
    fun `should return false when vehicles are not chosen for 4 planets`() {
        runBlocking {
            //given
            val viewModel = GameViewModel(Lazy { repository })

            //when
            viewModel.init()
            viewModel.onVehicleSelected(earth, starShip)
            viewModel.onVehicleSelected(jupiter, apollo)
            val result = viewModel.isSelectionComplete()

            //then
            assertThat(result).isFalse()
        }
    }

    // Failure cases to be added
    @Test
    fun `should return success Result when api call is successful`() {
        runBlocking {
            //given
            val viewModel = GameViewModel(Lazy { repository })
            val findResponse = Response.success<FindResponse>(
                FindResponse(
                    "success",
                    earth.name,
                    null
                )
            )
            whenever(
                repository.findFalcone(
                    listOf(earth.name!!, jupiter.name!!, mars.name!!, saturn.name!!),
                    listOf(starShip.name!!, apollo.name!!, apollo.name!!, falcon.name!!)
                )
            ).thenReturn(findResponse)

            //when
            viewModel.init()
            viewModel.onVehicleSelected(earth, starShip)
            viewModel.onVehicleSelected(jupiter, apollo)
            viewModel.onVehicleSelected(mars, apollo)
            viewModel.onVehicleSelected(saturn, falcon)
            viewModel.findFalcone()

            //then
            val result = viewModel.findResponse.getOrAwaitValue()
            assertThat(result).isEqualTo(
                Result.Success(
                    FindResponse(
                        "success",
                        earth.name,
                        null
                    )
                )
            )
        }
    }

    @Test
    fun `should return list of dto for the given planet`() {
        runBlocking {
            //given
            val viewModel = GameViewModel(Lazy { repository })

            //when
            viewModel.init()
            viewModel.onVehicleSelected(mars, apollo)
            viewModel.onVehicleSelected(saturn, falcon)
            val result = viewModel.getVehiclesDto(uranus)

            //then
            assertThat(result).isEqualTo(
                listOf(
                    VehicleDto(starShip, 1, false),
                    VehicleDto(apollo, 1, true),
                    VehicleDto(falcon, 2, true)
                )
            )
        }
    }
}