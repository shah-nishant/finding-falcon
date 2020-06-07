package shah.nishant.findingfalcone.game.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import shah.nishant.findingfalcone.databinding.VehicleLayoutBinding
import shah.nishant.findingfalcone.game.model.Target
import shah.nishant.findingfalcone.game.model.Vehicle
import shah.nishant.findingfalcone.game.model.VehicleDto

class VehicleAdapter constructor(
    private val target: Target,
    private val dtos: List<VehicleDto>,
    private val onVehicleSelected: (Vehicle) -> Unit
) : RecyclerView.Adapter<VehicleViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VehicleViewHolder {
        val binding =
            VehicleLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VehicleViewHolder(binding, onVehicleSelected)
    }

    override fun getItemCount() = dtos.size

    override fun onBindViewHolder(holder: VehicleViewHolder, position: Int) {
        val dto = dtos[position]
        holder.bind(dto, target.vehicle == dto.vehicle)
    }

}
