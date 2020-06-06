package shah.nishant.findingfalcon.game.view

import androidx.recyclerview.widget.RecyclerView
import shah.nishant.findingfalcon.databinding.LayoutPlanetBinding
import shah.nishant.findingfalcon.game.model.Planet

class PlanetViewHolder constructor(private val binding: LayoutPlanetBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(planet: Planet) {
        binding.apply {
            name.text = planet.name
            distance.text = planet.distance
        }
    }

}