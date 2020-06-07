package shah.nishant.findingfalcone.game.view

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.Lazy
import dagger.android.support.AndroidSupportInjection
import shah.nishant.findingfalcone.R
import shah.nishant.findingfalcone.databinding.GameFragmentBinding
import shah.nishant.findingfalcone.extensions.*
import shah.nishant.findingfalcone.game.model.Target
import shah.nishant.findingfalcone.game.viewmodel.GameViewModel
import shah.nishant.findingfalcone.viewmodel.ViewModelFactory
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
        binding.find.setOnClickListener { viewModel.findFalcone() }
        viewModel.init()
    }

    private fun setUpRecyclerView() {
        binding.planetList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = planetAdapter
        }
    }

    private fun initObservers() {
        // Load observer
        viewModel.gameMetaData.observe(viewLifecycleOwner, Observer {
            planetAdapter.setTargets(it.targets)
            binding.apply {
                successViews.visible()
                progressBar.gone()
            }
        })

        // Find observer
        viewModel.findResponse.observe(viewLifecycleOwner, Observer {
            if (it.isSuccessful()) {
                showShortToast(R.string.win_message)
            } else if (!it.error.isNullOrBlank()) {
                showShortToast(it.error)
            } else {
                showShortToast(R.string.lose_message)
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
