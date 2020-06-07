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
import shah.nishant.findingfalcone.coroutines.Result
import shah.nishant.findingfalcone.coroutines.getValue
import shah.nishant.findingfalcone.databinding.GameFragmentBinding
import shah.nishant.findingfalcone.extensions.*
import shah.nishant.findingfalcone.game.model.FindResponse
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
        binding.find.setOnClickListener {
            if (isConnectedToInternet()) {
                findFalcone()
            } else {
                showShortToast(R.string.no_internet)
            }
        }

        binding.retry.setOnClickListener { loadGameMetaData() }
        loadGameMetaData()
    }

    private fun findFalcone() {
        if (viewModel.isSelectionComplete()) {
            binding.progressBar.root.visible()
            viewModel.findFalcone()
        } else {
            showShortToast(R.string.incomplete_selection)
        }
    }

    private fun loadGameMetaData() {
        if (isConnectedToInternet()) {
            binding.apply {
                successViews.gone()
                errorViews.gone()
                progressBar.root.visible()
            }
            viewModel.init()
        } else {
            onLoadError()
            showShortToast(R.string.no_internet)
        }
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
                errorViews.gone()
                successViews.visible()
                progressBar.root.gone()
            }
        })

        //Load error observer
        viewModel.loadError.observe(viewLifecycleOwner, Observer {
            if (it) {
                onLoadError()
            }
        })

        // Find observer
        viewModel.findResponse.observe(viewLifecycleOwner, Observer {
            if (it?.isSuccessful() == true) {
                handleFindResult(it)
            } else {
                showShortToast(R.string.find_failure)
            }
        })
    }

    private fun onLoadError() {
        binding.apply {
            successViews.gone()
            progressBar.root.gone()
            errorViews.visible()
        }
    }

    private fun handleFindResult(result: Result<FindResponse>) {
        if (result.getValue()?.error.isNullOrBlank()) {
            binding.progressBar.root.gone()
            navigate(GameFragmentDirections.showResult(result.getValue()!!))
        } else {
            showShortToast(result.getValue()?.error!!)
        }
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
