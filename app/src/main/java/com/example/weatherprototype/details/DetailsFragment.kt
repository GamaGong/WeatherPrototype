package com.example.weatherprototype.details

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.weatherprototype.R
import com.example.weatherprototype.databinding.FragmentDetailsBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailsFragment : Fragment(R.layout.fragment_details) {

    // Without reflection
    private val viewBinding by viewBinding(FragmentDetailsBinding::bind)

    private val viewModel: DetailsViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.initialLoad()

        viewModel.currentWeather.observe(viewLifecycleOwner, {
            viewBinding.stub.text = it.toString()
        })

        viewModel.weatherForecast.observe(viewLifecycleOwner, {
            viewBinding.stub.text = it.toString()
        })

        viewModel.errors.observe(viewLifecycleOwner, {
            viewBinding.stub.text = it.toString()
        })
    }
}