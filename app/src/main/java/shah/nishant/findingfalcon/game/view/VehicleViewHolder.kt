package shah.nishant.findingfalcon.game.view

import androidx.recyclerview.widget.RecyclerView
import shah.nishant.findingfalcon.R
import shah.nishant.findingfalcon.databinding.VehicleLayoutBinding
import shah.nishant.findingfalcon.game.model.Vehicle

class VehicleViewHolder constructor(
    private val binding: VehicleLayoutBinding,
    private val selectVehicle: (String?) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(vehicle: Vehicle) {
        binding.apply {
            name.text = name.context.getString(R.string.vehicle_name, vehicle.name, vehicle.count.toString())
            details.text = details.context.getString(R.string.vehicle_details, vehicle.distance.toString(), vehicle.speed.toString())
        }
    }

}