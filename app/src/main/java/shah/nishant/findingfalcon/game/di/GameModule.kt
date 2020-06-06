package shah.nishant.findingfalcon.game.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import retrofit2.Retrofit
import shah.nishant.findingfalcon.game.data.GameApi
import shah.nishant.findingfalcon.game.view.GameFragment
import shah.nishant.findingfalcon.game.viewmodel.GameViewModel
import shah.nishant.findingfalcon.viewmodel.ViewModelKey

@Module
abstract class GameModule {

    @ContributesAndroidInjector
    abstract fun gameFragment(): GameFragment

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
