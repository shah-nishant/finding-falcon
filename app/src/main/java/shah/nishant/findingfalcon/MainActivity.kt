package shah.nishant.findingfalcon

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import dagger.Lazy
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import shah.nishant.findingfalcon.databinding.ActivityMainBinding
import shah.nishant.findingfalcon.extensions.viewLifecycleScoped
import javax.inject.Inject

class MainActivity : AppCompatActivity(), HasSupportFragmentInjector {

    @Inject
    lateinit var fragmentInjector: Lazy<DispatchingAndroidInjector<Fragment>>

    private val binding: ActivityMainBinding by viewLifecycleScoped(ActivityMainBinding::inflate)

    override fun supportFragmentInjector(): DispatchingAndroidInjector<Fragment> =
        fragmentInjector.get()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }

}
