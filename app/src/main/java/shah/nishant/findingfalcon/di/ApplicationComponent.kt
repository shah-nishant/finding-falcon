package shah.nishant.findingfalcon.di

import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.support.AndroidSupportInjectionModule
import shah.nishant.findingfalcon.FindingFalconApplication
import shah.nishant.findingfalcon.game.di.GameModule

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
