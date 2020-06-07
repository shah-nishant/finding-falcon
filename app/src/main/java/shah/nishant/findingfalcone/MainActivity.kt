package shah.nishant.findingfalcone

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import dagger.Lazy
import dagger.android.AndroidInjection
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import shah.nishant.findingfalcone.databinding.MainActivityBinding
import shah.nishant.findingfalcone.extensions.viewLifecycleScoped
import javax.inject.Inject

class MainActivity : AppCompatActivity(), HasSupportFragmentInjector {

    @Inject
    lateinit var fragmentInjector: Lazy<DispatchingAndroidInjector<Fragment>>

    private val binding: MainActivityBinding by viewLifecycleScoped(MainActivityBinding::inflate)

    override fun supportFragmentInjector(): DispatchingAndroidInjector<Fragment> =
        fragmentInjector.get()

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }

}
