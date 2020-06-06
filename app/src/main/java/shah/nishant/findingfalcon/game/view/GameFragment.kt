package shah.nishant.findingfalcon.game.view

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.Lazy
import dagger.android.support.AndroidSupportInjection
import shah.nishant.findingfalcon.R
import shah.nishant.findingfalcon.databinding.FragmentGameBinding
import shah.nishant.findingfalcon.extensions.createViewModel
import shah.nishant.findingfalcon.extensions.viewLifecycleScoped
import shah.nishant.findingfalcon.game.viewmodel.GameViewModel
import shah.nishant.findingfalcon.viewmodel.ViewModelFactory
import javax.inject.Inject

class GameFragment : Fragment(R.layout.fragment_game) {

    private val binding: FragmentGameBinding by viewLifecycleScoped(FragmentGameBinding::bind)

    @Inject
    lateinit var viewModelFactory: Lazy<ViewModelFactory>

    private val viewModel by lazy {
        createViewModel<GameViewModel>(viewModelFactory.get())
    }

    private val planetAdapter = PlanetListAdapter()

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerView()
        initObservers()
        viewModel.init()
    }

    private fun setUpRecyclerView() {
        binding.planetList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = planetAdapter
        }
    }

    private fun initObservers() {
        viewModel.gameMetaData.observe(viewLifecycleOwner, Observer {
            planetAdapter.setPlanets(it.planets)
        })
    }

}
