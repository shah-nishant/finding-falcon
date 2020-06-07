package shah.nishant.findingfalcone.game.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import retrofit2.Retrofit
import shah.nishant.findingfalcone.game.data.GameApi
import shah.nishant.findingfalcone.game.view.GameFragment
import shah.nishant.findingfalcone.game.view.VehicleBottomSheet
import shah.nishant.findingfalcone.game.viewmodel.GameViewModel
import shah.nishant.findingfalcone.viewmodel.ViewModelKey

@Module
abstract class GameModule {

    @ContributesAndroidInjector
    abstract fun gameFragment(): GameFragment

    @ContributesAndroidInjector
    abstract fun vehicleBottomSheet(): VehicleBottomSheet

    @Binds
    @IntoMap
    @ViewModelKey(GameViewModel::class)
    abstract fun gameViewModel(myViewModel: GameViewModel): ViewModel

    @Module
    companion object {

        @Provides
        @JvmStatic
        fun gameApi(retrofit: Retrofit): GameApi = retrofit.create(GameApi::class.java)

    }
}
