package shah.nishant.findingfalcon.game.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.Lazy
import dagger.android.support.AndroidSupportInjection
import shah.nishant.findingfalcon.databinding.VehicleBottomSheetBinding
import shah.nishant.findingfalcon.extensions.gone
import shah.nishant.findingfalcon.extensions.viewLifecycleScoped
import shah.nishant.findingfalcon.extensions.visible
import shah.nishant.findingfalcon.game.model.Vehicle
import shah.nishant.findingfalcon.game.viewmodel.GameViewModel
import shah.nishant.findingfalcon.viewmodel.ViewModelFactory
import javax.inject.Inject

class VehicleBottomSheet : BottomSheetDialogFragment() {

    private val args: VehicleBottomSheetArgs by navArgs()
    private val binding: VehicleBottomSheetBinding by viewLifecycleScoped(VehicleBottomSheetBinding::bind)

    @Inject
    lateinit var viewModelFactory: Lazy<ViewModelFactory>

    private val viewModel: GameViewModel by lazy {
        // Sharing viewmodel
        ViewModelProvider(requireActivity(), viewModelFactory.get()).get(GameViewModel::class.java)
    }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return VehicleBottomSheetBinding.inflate(layoutInflater).root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerView()
        binding.remove.apply {
            if (args.target.vehicle == null) {
                gone()
            } else {
                visible()
                setOnClickListener { onVehicleRemoved() }
            }
        }
    }

    private fun setUpRecyclerView() {
        binding.vehicleList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = VehicleAdapter(
                viewModel.gameMetaData.value!!.vehicles,
                this@VehicleBottomSheet::onVehicleSelected
            )
        }
    }

    private fun onVehicleSelected(vehicle: Vehicle) {
        viewModel.onVehicleSelected(args.target.planet, vehicle)
        dismiss()
    }

    private fun onVehicleRemoved() {
        viewModel.onVehicleRemoved(args.target.planet)
        dismiss()
    }
}
