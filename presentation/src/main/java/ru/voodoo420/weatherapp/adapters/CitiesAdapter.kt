package ru.voodoo420.weatherapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_city.*
import ru.voodoo420.domain.entities.City
import ru.voodoo420.weatherapp.R
import ru.voodoo420.domain.entities.CityCurrentWeather
import ru.voodoo420.domain.entities.Coord

import kotlin.collections.ArrayList

class CitiesAdapter(
    private val coordToFunction: (Coord) -> Unit,
    private val idToFunction: (City) -> Unit
) :
    RecyclerView.Adapter<CitiesAdapter.ViewHolder>() {

    private val cities: MutableList<CityCurrentWeather> = ArrayList()

    fun setData(cities: List<CityCurrentWeather>) {
        this.cities.clear()
        this.cities.addAll(cities)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_city, parent, false)
        )
    }

    override fun getItemCount(): Int = cities.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentWeather = cities[position]
        holder.apply {
            bind(currentWeather)
            itemView.setOnClickListener {
                currentWeather.city.coord.apply {
                    coordToFunction(
                        Coord(lat, lon)
                    )
                }
            }
            itemView.setOnLongClickListener {
                idToFunction(currentWeather.city)
                return@setOnLongClickListener true
            }
        }
    }

    inner class ViewHolder(override val containerView: View) :
        RecyclerView.ViewHolder(containerView), LayoutContainer {
        fun bind(item: CityCurrentWeather) = with(item) {
            Glide.with(containerView)
                .load("https://openweathermap.org/img/wn/${weather.icon}@2x.png")
                .into(cities_icon)
            cities_name.text = city.name
            cities_temperature.text = weather.temperature.toString()
        }
    }
}
