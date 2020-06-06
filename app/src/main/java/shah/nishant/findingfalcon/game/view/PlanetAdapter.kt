package shah.nishant.findingfalcon.game.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import shah.nishant.findingfalcon.databinding.PlanetLayoutBinding
import shah.nishant.findingfalcon.game.model.Planet

class PlanetAdapter constructor(private val selectVehicle: (String?) -> Unit) : RecyclerView.Adapter<PlanetViewHolder>() {

    private val planets = mutableListOf<Planet>()

    // Not using diff utils since, this list will not never get updated until we restart the app
    fun setPlanets(newPlanets: List<Planet>) {
        planets.clear()
        planets.addAll(newPlanets)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlanetViewHolder {
        val binding =
            PlanetLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PlanetViewHolder(binding, selectVehicle)
    }

    override fun getItemCount() = planets.size

    override fun onBindViewHolder(holder: PlanetViewHolder, position: Int) {
        holder.bind(planets[position])
    }


}