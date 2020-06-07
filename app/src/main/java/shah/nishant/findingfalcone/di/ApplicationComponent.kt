package shah.nishant.findingfalcone.di

import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.support.AndroidSupportInjectionModule
import shah.nishant.findingfalcone.FindingFalconApplication
import shah.nishant.findingfalcone.game.di.GameModule

@Component(
    modules = [
        // android injection
        AndroidInjectionModule::class,
        AndroidSupportInjectionModule::class,
        ApplicationModule::class,
        GameModule::class
    ]
)
interface ApplicationComponent {
    @Component.Builder
    interface Builder {
        fun build(): ApplicationComponent

        @BindsInstance
        fun application(application: FindingFalconApplication): Builder
    }

    fun inject(application: FindingFalconApplication)
}
