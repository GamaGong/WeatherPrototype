package com.example.weatherprototype.app.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.bumptech.glide.Glide
import com.example.weatherprototype.R
import com.example.weatherprototype.databinding.WeatherListScreenHeaderItemBinding
import com.example.weatherprototype.databinding.WeatherListScreenLocationItemBinding

class WeatherListAdapter : RecyclerView.Adapter<WeatherListAdapter.Holder>() {
    private val items = mutableListOf<WeatherListItem>()

    fun updateItems(newItems: List<WeatherListItem>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }

    sealed class Holder(viewBinding: ViewBinding) : RecyclerView.ViewHolder(viewBinding.root) {

        companion object {
            private val cardColors = listOf(
                R.color.purple_200,
                R.color.green_200,
                R.color.red_200,
                R.color.blue_200
            )
        }

        class Header(private val binding: WeatherListScreenHeaderItemBinding) :
            Holder(binding) {
            fun bindTo(item: WeatherListItem.Header) {
                binding.title.text = itemView.context.getString(item.titleId)
            }
        }

        class Location(private val binding: WeatherListScreenLocationItemBinding) :
            Holder(binding) {
            fun bindTo(item: WeatherListItem.LocationWeather, position: Int) {
                binding.apply {
                    name.text = item.name
                    temperature.text = item.temperature
                    Glide.with(this@Location.itemView)
                        .load(item.weatherIconUrl.regular)
                        .into(icon)
                    card.setOnClickListener {
                        it.findNavController()
                            .navigate(
                                WeatherListFragmentDirections.actionListFragmentToDetailsFragment(
                                    item.toDomain()
                                )
                            )
                    }
                    card.setCardBackgroundColor(
                        root.context.getColor(
                            cardColors[(position + 1) % cardColors.size]
                        )
                    )
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            R.layout.weather_list_screen_header_item -> Holder.Header(
                WeatherListScreenHeaderItemBinding.inflate(inflater, parent, false)
            )
            R.layout.weather_list_screen_location_item -> Holder.Location(
                WeatherListScreenLocationItemBinding.inflate(inflater, parent, false)
            )
            else -> throw IllegalStateException("Add holder for viewType: $viewType")
        }
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        when (holder) {
            is Holder.Header -> holder.bindTo(items[position] as WeatherListItem.Header)
            is Holder.Location -> holder.bindTo(
                items[position] as WeatherListItem.LocationWeather,
                position
            )
        }
    }

    override fun getItemCount(): Int = items.size

    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is WeatherListItem.Header -> R.layout.weather_list_screen_header_item
            is WeatherListItem.LocationWeather -> R.layout.weather_list_screen_location_item
        }
    }
}