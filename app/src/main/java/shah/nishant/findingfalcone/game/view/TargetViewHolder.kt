package shah.nishant.findingfalcone.game.view

import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import shah.nishant.findingfalcone.R
import shah.nishant.findingfalcone.databinding.TargetLayoutBinding
import shah.nishant.findingfalcone.extensions.gone
import shah.nishant.findingfalcone.extensions.invisible
import shah.nishant.findingfalcone.extensions.visible
import shah.nishant.findingfalcone.game.model.Target

class TargetViewHolder constructor(
    private val binding: TargetLayoutBinding,
    private val selectVehicle: (Target) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    companion object {
        private val PLANET_DRAWABLES = arrayOf(
            R.drawable.planet_1,
            R.drawable.planet_2,
            R.drawable.planet_3,
            R.drawable.planet_4,
            R.drawable.planet_5,
            R.drawable.planet_6
        )
    }

    fun bind(target: Target) {
        binding.apply {
            name.text = target.planet.name
            distance.text = distance.context.getString(R.string.distance, target.planet.distance.toString())
            photo.setImageResource(PLANET_DRAWABLES[adapterPosition % PLANET_DRAWABLES.size])
            if (target.vehicle == null) {
                selectVehicle.setText(R.string.select_vehicle)
                selectVehicle.setTextColor(selectVehicle.context.resources.getColor(R.color.black))
                selectVehicle.backgroundTintList =
                    ContextCompat.getColorStateList(selectVehicle.context, R.color.white)
                timeTaken.invisible()
            } else {
                selectVehicle.text = target.vehicle.name
                selectVehicle.setTextColor(selectVehicle.context.resources.getColor(R.color.white))
                selectVehicle.backgroundTintList =
                    ContextCompat.getColorStateList(selectVehicle.context, R.color.green)
                timeTaken.text = timeTaken.context.getString(
                    R.string.time_taken,
                    // Hope API would never respond with vehicle.distance = 0
                    (target.planet.distance!! / target.vehicle.speed!!).toString()
                )
                timeTaken.visible()
            }
            selectVehicle.setOnClickListener { selectVehicle(target) }
        }
    }

}