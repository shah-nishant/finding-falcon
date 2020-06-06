package shah.nishant.findingfalcon.game.view

import androidx.recyclerview.widget.RecyclerView
import shah.nishant.findingfalcon.R
import shah.nishant.findingfalcon.databinding.PlanetLayoutBinding
import shah.nishant.findingfalcon.game.model.Planet

class PlanetViewHolder constructor(
    private val binding: PlanetLayoutBinding,
    private val selectVehicle: (String?) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    companion object {
        private val PLANET_DRAWABLES = arrayOf(
            R.drawable.planet_1,
            R.drawable.planet_2,
            R.drawable.planet_3,
            R.drawable.planet_4
        )
    }


    fun bind(planet: Planet) {
        binding.apply {
            name.text = planet.name
            distance.text = distance.context.getString(R.string.distance, planet.distance)
            photo.setImageResource(PLANET_DRAWABLES.random())
            selectVehicle.setOnClickListener { selectVehicle(planet.name) }
        }
    }

}