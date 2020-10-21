package com.example.weatherprototype.app.details.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.example.weatherprototype.R
import com.example.weatherprototype.app.IconUrl
import com.example.weatherprototype.app.details.util.ToolbarOffsetChangedListener
import com.example.weatherprototype.databinding.ViewDetailsAppBarBinding
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
        binding.apply {
            shimmerLayout.isVisible = true
            sunriseLabel.isVisible = false
            sunsetLabel.isVisible = false
            shimmerLayout.startShimmer()
        }
    }

    fun unlock() {
        binding.apply {
            shimmerLayout.isVisible = false
            sunriseLabel.isVisible = true
            sunsetLabel.isVisible = true
            shimmerLayout.stopShimmer()
        }
    }

    fun fill(weather: HeaderWeather) {
        binding.apply {
            sunriseTime.text = weather.sunriseTime.format(DateTimeFormatter.ISO_LOCAL_TIME)
            sunsetTime.text = weather.sunsetTime.format(DateTimeFormatter.ISO_LOCAL_TIME)
            temperature.text = weather.temperature
            weatherCondition.text = weather.description
            windSpeed.text = weather.windSpeed
            detailsTitle.text = weather.weatherLocationName
            featuredStar.background = if (weather.isFeatured) {
                ResourcesCompat.getDrawable(resources, R.drawable.ic_baseline_star_24, null)
            } else {
                ResourcesCompat.getDrawable(resources, R.drawable.ic_baseline_star_outline_24, null)
            }
            Glide.with(this@DetailsAppBar)
                .load(weather.iconUrl.large)
                .into(weatherIcon)
        }
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
    val isFeatured: Boolean,
)