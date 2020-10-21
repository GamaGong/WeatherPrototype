package com.example.weatherprototype.app.details

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.weatherprototype.R
import com.example.weatherprototype.app.details.pager.DetailsPagerAdapter
import com.example.weatherprototype.databinding.FragmentDetailsBinding
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailsFragment : Fragment(R.layout.fragment_details) {

    // Without reflection
    private val viewBinding by viewBinding(FragmentDetailsBinding::bind)

    private val args: DetailsFragmentArgs by navArgs()

    private val viewModel: DetailsViewModel by viewModel()

    private val weatherPagerAdapter = DetailsPagerAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.initialLoad(args.location)
        viewBinding.appBarLayout.lock()

        viewModel.currentWeather.observe(viewLifecycleOwner, {
            viewBinding.appBarLayout.fill(it)
            viewBinding.appBarLayout.unlock()
        })

        viewModel.weatherForecast.observe(viewLifecycleOwner, {
            TabLayoutMediator(viewBinding.tabLayout, viewBinding.content) { tab, position ->
                tab.text = String.format(
                    resources.getString(R.string.days_pattern),
                    it[position].listItems.size
                )
            }.attach()

            weatherPagerAdapter.updateItems(it)
        })

        viewModel.errors.observe(viewLifecycleOwner, {
            Snackbar.make(
                viewBinding.detailsContainer,
                it.message ?: "Unknown error",
                Snackbar.LENGTH_SHORT
            ).show()
        })

        viewBinding.appBarLayout.onBackPressed = {
            it.findNavController().popBackStack()
        }
        viewBinding.appBarLayout.onFeaturedChecked = {
            viewModel.featuredChecked()
        }

        viewBinding.content.apply {
            adapter = weatherPagerAdapter
        }
    }
}