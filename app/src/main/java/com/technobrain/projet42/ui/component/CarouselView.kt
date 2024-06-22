package com.technobrain.projet42.ui.component

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.viewinterop.AndroidView
import androidx.viewpager2.widget.ViewPager2
import com.technobrain.projet42.domain.model.EventShort

@Composable
fun CarouselView(events: List<EventShort>, context: Context) {
    val pagerAdapter = remember { EventPagerAdapter(events, context) }
    AndroidView(
        factory = { ctx ->
            ViewPager2(ctx).apply {
                adapter = pagerAdapter
                orientation = ViewPager2.ORIENTATION_HORIZONTAL
            }
        }
    )
}