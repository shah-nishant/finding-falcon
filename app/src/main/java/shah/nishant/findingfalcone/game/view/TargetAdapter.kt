package shah.nishant.findingfalcone.game.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import shah.nishant.findingfalcone.databinding.TargetLayoutBinding
import shah.nishant.findingfalcone.game.model.Target

class TargetAdapter constructor(private val selectVehicle: (Target) -> Unit) : RecyclerView.Adapter<TargetViewHolder>() {

    private val targets = mutableListOf<Target>()

    fun setTargets(newTargets: List<Target>) {
        val diffCallback = TargetDiffUtilCallBack(targets, newTargets)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        targets.clear()
        targets.addAll(newTargets)
        diffResult.dispatchUpdatesTo(this)
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
