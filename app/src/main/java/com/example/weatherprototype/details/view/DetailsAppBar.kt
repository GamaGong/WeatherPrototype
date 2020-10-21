package com.example.weatherprototype.details.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.example.weatherprototype.IconUrl
import com.example.weatherprototype.R
import com.example.weatherprototype.databinding.ViewDetailsAppBarBinding
import com.example.weatherprototype.details.util.ToolbarOffsetChangedListener
import com.google.android.material.appbar.AppBarLayout
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class DetailsAppBar : AppBarLayout {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    private var _binding: ViewDetailsAppBarBinding? = null
    private val binding get() = _binding!!

    var onBackPressed: (View) -> Unit = {}
    var onFeaturedChecked: () -> Unit = {}

    init {
        _binding = ViewDetailsAppBarBinding.inflate(LayoutInflater.from(context), this)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        addOnOffsetChangedListener(
            ToolbarOffsetChangedListener(binding.toolbarContent, binding.toolbar, 0F)
        )
        binding.back.setOnClickListener { onBackPressed(it) }
        binding.featuredStar.setOnClickListener { onFeaturedChecked() }
    }

    fun lock() {
        binding.shimmerLayout.isVisible = true
        binding.sunriseLabel.isVisible = false
        binding.sunsetLabel.isVisible = false
        binding.shimmerLayout.startShimmer()
    }

    fun unlock() {
        binding.shimmerLayout.isVisible = false
        binding.sunriseLabel.isVisible = true
        binding.sunsetLabel.isVisible = true
        binding.shimmerLayout.stopShimmer()
    }

    fun fill(weather: HeaderWeather) {
        binding.sunriseTime.text = weather.sunriseTime.format(DateTimeFormatter.ISO_LOCAL_TIME)
        binding.sunsetTime.text = weather.sunsetTime.format(DateTimeFormatter.ISO_LOCAL_TIME)
        binding.temperature.text = weather.temperature
        binding.weatherCondition.text = weather.description
        binding.windSpeed.text = weather.windSpeed
        binding.detailsTitle.text = weather.weatherLocationName
        binding.featuredStar.background = if (weather.isFeatured) {
            ResourcesCompat.getDrawable(resources, R.drawable.ic_baseline_star_24, null)
        } else {
            ResourcesCompat.getDrawable(resources, R.drawable.ic_baseline_star_outline_24, null)
        }
        Glide.with(this)
            .load(weather.iconUrl.large)
            .into(binding.weatherIcon)
    }
}


data class HeaderWeather(
    val temperature: String,
    val windSpeed: String,
    val iconUrl: IconUrl,
    val description: String,
    val sunriseTime: LocalDateTime,
    val sunsetTime: LocalDateTime,
    val weatherLocationName: String,
    val isFeatured: Boolean
)