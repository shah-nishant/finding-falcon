package shah.nishant.findingfalcon.game.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import shah.nishant.findingfalcon.databinding.VehicleBottomSheetBinding
import shah.nishant.findingfalcon.extensions.viewLifecycleScoped

class VehicleBottomSheet : BottomSheetDialogFragment() {

    private val args: VehicleBottomSheetArgs by navArgs()
    private val binding: VehicleBottomSheetBinding by viewLifecycleScoped(VehicleBottomSheetBinding::bind)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return VehicleBottomSheetBinding.inflate(layoutInflater).root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerView()
    }

    private fun setUpRecyclerView() {
        binding.vehicleList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = VehicleAdapter(args.vehicles.asList(), {})
        }
    }
}
