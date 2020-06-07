package shah.nishant.findingfalcone.game.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import shah.nishant.findingfalcone.databinding.VehicleLayoutBinding
import shah.nishant.findingfalcone.game.model.Vehicle

class VehicleAdapter constructor(
    private val vehicles: List<Vehicle>,
    private val onVehicleSelected: (Vehicle) -> Unit
) : RecyclerView.Adapter<VehicleViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VehicleViewHolder {
        val binding =
            VehicleLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VehicleViewHolder(binding, onVehicleSelected)
    }

    override fun getItemCount() = vehicles.size

    override fun onBindViewHolder(holder: VehicleViewHolder, position: Int) {
        holder.bind(vehicles[position])
    }

}
