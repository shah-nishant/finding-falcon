package shah.nishant.findingfalcon.game.view

import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import shah.nishant.findingfalcon.R
import shah.nishant.findingfalcon.databinding.TargetLayoutBinding
import shah.nishant.findingfalcon.game.model.Target

class TargetViewHolder constructor(
    private val binding: TargetLayoutBinding,
    private val selectVehicle: (Target) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    companion object {
        private val PLANET_DRAWABLES = arrayOf(
            R.drawable.planet_1,
            R.drawable.planet_2,
            R.drawable.planet_3,
            R.drawable.planet_4
        )
    }

    fun bind(target: Target) {
        binding.apply {
            name.text = target.planet.name
            distance.text = distance.context.getString(R.string.distance, target.planet.distance)
            photo.setImageResource(PLANET_DRAWABLES[adapterPosition % PLANET_DRAWABLES.size])
            if (target.vehicle == null) {
                selectVehicle.setText(R.string.select_vehicle)
                selectVehicle.setTextColor(selectVehicle.context.resources.getColor(R.color.black))
                selectVehicle.backgroundTintList =
                    ContextCompat.getColorStateList(selectVehicle.context, R.color.white)
            } else {
                selectVehicle.text = target.vehicle.name
                selectVehicle.setTextColor(selectVehicle.context.resources.getColor(R.color.white))
                selectVehicle.backgroundTintList =
                    ContextCompat.getColorStateList(selectVehicle.context, R.color.green)
            }
            selectVehicle.setOnClickListener { selectVehicle(target) }
        }
    }

}