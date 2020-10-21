package com.example.weatherprototype.app.list

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.weatherprototype.R
import com.example.weatherprototype.databinding.FragmentWeatherListBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import org.koin.androidx.viewmodel.ext.android.viewModel

class WeatherListFragment : Fragment(R.layout.fragment_weather_list) {
    private val viewBinding by viewBinding(FragmentWeatherListBinding::bind)

    @FlowPreview
    @ExperimentalCoroutinesApi
    private val viewModel: WeatherListViewModel by viewModel()

    @ExperimentalCoroutinesApi
    @FlowPreview
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = WeatherListAdapter()
        viewBinding.searchButton.setOnClickListener {
            findNavController().navigate(WeatherListFragmentDirections.actionListFragmentToSearchDialog())
        }
        viewBinding.recycler.adapter = adapter
        viewModel.items.observe(viewLifecycleOwner) { adapter.updateItems(it) }
        viewModel.errors.observe(viewLifecycleOwner) {
            Snackbar.make(
                viewBinding.root,
                it.message ?: "Unknown error",
                Snackbar.LENGTH_SHORT
            ).show()
        }
    }
}