package com.example.weatherprototype.details.util

import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import com.google.android.material.appbar.AppBarLayout
import kotlin.math.abs

class ToolbarOffsetChangedListener(
    private val content: ViewGroup,
    private val collapsedToolbar: Toolbar,
    private val percentageToShowCollapsed: Float
) : AppBarLayout.OnOffsetChangedListener {

    override fun onOffsetChanged(appBarLayout: AppBarLayout, offset: Int) {
        val maxScroll = appBarLayout.totalScrollRange
        val percentage = (abs(offset).toFloat() / maxScroll)

        handleToolbarVisibility(percentage)
    }

    private fun handleToolbarVisibility(percentage: Float) {
        when {
            percentage == 0f -> {
                content.alpha = 1f
                collapsedToolbar.background.alpha = 0
            }
            percentage == 1f -> {
                content.alpha = 0f
                collapsedToolbar.background.alpha = 255
            }
            percentage < percentageToShowCollapsed -> {
                content.alpha = 1f - percentage
                collapsedToolbar.background.alpha = 0
            }
            percentage >= percentageToShowCollapsed -> {
                content.alpha = (1f - percentage)
                collapsedToolbar.background.alpha = percentage.times(255).toInt()
            }
            else -> {
//                do nothing
            }
        }
    }
}