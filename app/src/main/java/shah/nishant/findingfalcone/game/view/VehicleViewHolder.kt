package shah.nishant.findingfalcone.game.view

import androidx.recyclerview.widget.RecyclerView
import shah.nishant.findingfalcone.R
import shah.nishant.findingfalcone.databinding.VehicleLayoutBinding
import shah.nishant.findingfalcone.game.model.Vehicle

class VehicleViewHolder constructor(
    private val binding: VehicleLayoutBinding,
    private val onVehicleSelected: (Vehicle) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(vehicle: Vehicle) {
        binding.apply {
            name.text = name.context.getString(R.string.vehicle_name, vehicle.name, vehicle.count.toString())
            details.text = details.context.getString(R.string.vehicle_details, vehicle.distance.toString(), vehicle.speed.toString())
            root.setOnClickListener { onVehicleSelected(vehicle) }
        }
    }

}