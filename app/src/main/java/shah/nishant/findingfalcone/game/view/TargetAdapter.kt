package shah.nishant.findingfalcone.game.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import shah.nishant.findingfalcone.databinding.TargetLayoutBinding
import shah.nishant.findingfalcone.game.model.Target

class TargetAdapter constructor(private val selectVehicle: (Target) -> Unit) : RecyclerView.Adapter<TargetViewHolder>() {

    private val targets = mutableListOf<Target>()

    // Not using diff utils since, this list will not never get updated until we restart the app
    fun setTargets(newTargets: List<Target>) {
        targets.clear()
        targets.addAll(newTargets)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TargetViewHolder {
        val binding =
            TargetLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TargetViewHolder(binding, selectVehicle)
    }

    override fun getItemCount() = targets.size

    override fun onBindViewHolder(holder: TargetViewHolder, position: Int) {
        holder.bind(targets[position])
    }


}
