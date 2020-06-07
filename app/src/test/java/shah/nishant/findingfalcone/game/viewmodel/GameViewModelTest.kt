package shah.nishant.findingfalcone.game.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import dagger.Lazy
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test
import shah.nishant.findingfalcone.game.data.GameRepository
import shah.nishant.findingfalcone.game.model.GameMetaData
import shah.nishant.findingfalcone.game.model.Planet
import shah.nishant.findingfalcone.game.model.Target
import shah.nishant.findingfalcone.game.model.Vehicle

class GameViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val repository = mock<GameRepository>()
    private val viewModel = GameViewModel(Lazy { repository })

    @Test
    fun `should return load error false and game meta data when api calls are successful`() {
        runBlocking {
            //given
            val planets = listOf(
                Planet("A", 100),
                Planet("B", 200),
                Planet("C", 300)
            )
            val vehicles = listOf(
                Vehicle("A", 1, 100, 2),
                Vehicle("B", 2, 200, 3),
                Vehicle("C", 3, 300, 4)
            )
            whenever(repository.getPlanets()).thenReturn(planets)
            whenever(repository.getVehicle()).thenReturn(vehicles)

            viewModel.loadError.observeForever(object : Observer<Boolean> {
                override fun onChanged(error: Boolean?) {
                    assertThat(error).isFalse()
                    viewModel.loadError.removeObserver(this)
                }
            })

            viewModel.gameMetaData.observeForever(object : Observer<GameMetaData> {
                override fun onChanged(metaData: GameMetaData?) {
                    assertThat(metaData).isEqualTo(
                        GameMetaData(
                            planets.map { p -> Target(p) },
                            vehicles
                        )
                    )
                    viewModel.gameMetaData.removeObserver(this)
                }
            })
        }
    }
}