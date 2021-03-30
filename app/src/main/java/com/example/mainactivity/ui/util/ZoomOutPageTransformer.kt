package com.example.mainactivity.ui.util

import android.view.View
import androidx.viewpager2.widget.ViewPager2
import com.example.mainactivity.R
import kotlin.math.abs

class ZoomOutPageTransformer : ViewPager2.PageTransformer {

    override fun transformPage(view: View, position: Float) {
        view.apply {
            val nextItemVisiblePx = resources.getDimension(R.dimen.next_viewpager_padding)
            val currentItemHorizontalMarginPx = resources.getDimension(R.dimen.current_viewpager_horizontal_margin)
            val pageTranslationX = nextItemVisiblePx + currentItemHorizontalMarginPx
            view.translationX = -pageTranslationX * position
            view.scaleY = 1 - (0.35f * abs(position))
        }
    }
}