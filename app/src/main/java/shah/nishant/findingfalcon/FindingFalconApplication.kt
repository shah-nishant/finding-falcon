package shah.nishant.findingfalcon

import android.app.Activity
import android.app.Application
import dagger.Lazy
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import shah.nishant.findingfalcon.di.DaggerApplicationComponent
import javax.inject.Inject

class FindingFalconApplication: Application(), HasActivityInjector {

    @Inject
    lateinit var activityInjector: Lazy<DispatchingAndroidInjector<Activity>>

    override fun activityInjector(): DispatchingAndroidInjector<Activity> = activityInjector.get()

    override fun onCreate() {
        super.onCreate()
        DaggerApplicationComponent.builder().application(this).build().inject(this)
    }

}
