package com.example.weatherprototype

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.example.weatherprototype.databinding.FragmentWeatherListBinding
import com.example.weatherprototype.databinding.WeatherListScreenHeaderItemBinding
import com.example.weatherprototype.databinding.WeatherListScreenLocationItemBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.example.weatherprototype.Location as DomainLocation

class WeatherListFragment : Fragment(R.layout.fragment_weather_list) {
    private val viewBinding by viewBinding(FragmentWeatherListBinding::bind)

    private val viewModel: ViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = Adapter()
        viewBinding.recycler.adapter = adapter
        viewModel.items.observe(viewLifecycleOwner) { adapter.updateItems(it) }
    }

    class Adapter : RecyclerView.Adapter<Adapter.Holder>() {
        private val items = mutableListOf<Item>()

        fun updateItems(newItems: List<Item>) {
            items.clear()
            items.addAll(newItems)
            notifyDataSetChanged()
        }

        sealed class Holder(viewBinding: ViewBinding) : RecyclerView.ViewHolder(viewBinding.root) {
            class Header(private val binding: WeatherListScreenHeaderItemBinding) : Holder(binding) {
                fun bindTo(item: Item.Header) {
                    binding.title.text = item.title
                }
            }

            class Location(private val binding: WeatherListScreenLocationItemBinding) : Holder(binding) {
                fun bindTo(item: Item.Location) {
                    binding.apply {
                        name.text = item.name
                        temperature.text = item.temperature
                        Glide.with(this@Location.itemView)
                            .load(item.weatherIconUrl.regular)
                            .into(icon)
                        root.setOnClickListener {
                            it.findNavController()
                                .navigate(WeatherListFragmentDirections.actionListFragmentToDetailsFragment(item.toDomain()))
                        }
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
                is Holder.Header -> holder.bindTo(items[position] as Item.Header)
                is Holder.Location -> holder.bindTo(items[position] as Item.Location)
            }
        }

        override fun getItemCount(): Int = items.size

        override fun getItemViewType(position: Int): Int {
            return when (items[position]) {
                is Item.Header -> R.layout.weather_list_screen_header_item
                is Item.Location -> R.layout.weather_list_screen_location_item
            }
        }
    }

    class ViewModel : androidx.lifecycle.ViewModel() {
        private val _items = MutableLiveData<List<Item>>()
        val items: LiveData<List<Item>> get() = _items

        init {
            _items.value = listOf(
                Item.Header(title = "Favorites"),
                Item.Location(
                    name = "London",
                    temperature = "+6",
                    weatherIconUrl = IconUrl("10d"),
                    coordinates = Coordinates(latitude = 51.51, longitude = -0.07),
                ),
                Item.Location(
                    name = "London",
                    temperature = "+6",
                    weatherIconUrl = IconUrl("10d"),
                    coordinates = Coordinates(latitude = 51.51, longitude = -0.07),
                ),
                Item.Location(
                    name = "London",
                    temperature = "+6",
                    weatherIconUrl = IconUrl("10d"),
                    coordinates = Coordinates(latitude = 51.51, longitude = -0.07),
                ),
                Item.Location(
                    name = "London",
                    temperature = "+6",
                    weatherIconUrl = IconUrl("10d"),
                    coordinates = Coordinates(latitude = 51.51, longitude = -0.07),
                ),
            )
        }
    }

    sealed class Item {
        data class Header(val title: String) : Item()
        data class Location(
            val name: String,
            val temperature: String,
            val weatherIconUrl: IconUrl,
            val coordinates: Coordinates,
        ) : Item() {
            fun toDomain() = DomainLocation(
                name = name,
                coordinates = coordinates,
            )
        }
    }
}