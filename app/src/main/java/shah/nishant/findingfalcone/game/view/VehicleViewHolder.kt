package shah.nishant.findingfalcone.game.view

import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.vehicle_layout.view.*
import shah.nishant.findingfalcone.R
import shah.nishant.findingfalcone.databinding.VehicleLayoutBinding
import shah.nishant.findingfalcone.game.model.Vehicle
import shah.nishant.findingfalcone.game.model.VehicleDto

class VehicleViewHolder constructor(
    private val binding: VehicleLayoutBinding,
    private val onVehicleSelected: (Vehicle) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(dto: VehicleDto, isSelected: Boolean) {
        binding.apply {
            root.isEnabled = dto.isSelectable()
            root.name.isEnabled = dto.isSelectable()
            root.details.isEnabled = dto.isSelectable()
            val vehicle = dto.vehicle
            name.text = name.context.getString(R.string.vehicle_name, vehicle.name, dto.availableNumber.toString())
            details.text = details.context.getString(R.string.vehicle_details, vehicle.distance.toString(), vehicle.speed.toString())
            radio.isChecked = isSelected
            root.setOnClickListener { onVehicleSelected(vehicle) }
        }
    }

}