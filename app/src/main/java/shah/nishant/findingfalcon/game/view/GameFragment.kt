package shah.nishant.findingfalcon.game.view

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.Lazy
import dagger.android.support.AndroidSupportInjection
import shah.nishant.findingfalcon.R
import shah.nishant.findingfalcon.databinding.GameFragmentBinding
import shah.nishant.findingfalcon.extensions.*
import shah.nishant.findingfalcon.game.model.Target
import shah.nishant.findingfalcon.game.viewmodel.GameViewModel
import shah.nishant.findingfalcon.viewmodel.ViewModelFactory
import javax.inject.Inject

class GameFragment : Fragment(R.layout.game_fragment) {

    private val binding: GameFragmentBinding by viewLifecycleScoped(GameFragmentBinding::bind)

    @Inject
    lateinit var viewModelFactory: Lazy<ViewModelFactory>

    private val viewModel: GameViewModel by lazy {
        // Sharing viewmodel
        ViewModelProvider(requireActivity(), viewModelFactory.get()).get(GameViewModel::class.java)
    }

    private val planetAdapter = TargetAdapter(this::selectVehicle)

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
            planetAdapter.setTargets(it.targets)
            binding.apply {
                successViews.visible()
                progressBar.gone()
            }
        })
    }

    private fun selectVehicle(target: Target) {
        if (target.vehicle != null) {
            // Change vehicle
            navigate(GameFragmentDirections.selectVehicle(target))
        } else {
            // Select vehicle
            if (viewModel.isSelectionComplete()) {
                showShortToast(R.string.select_vehicle_failure)
            } else {
                navigate(GameFragmentDirections.selectVehicle(target))
            }
        }
    }

}
