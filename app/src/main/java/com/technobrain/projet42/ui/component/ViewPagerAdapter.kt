package com.technobrain.projet42.ui.component

import android.content.Context
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.recyclerview.widget.RecyclerView
import com.technobrain.projet42.domain.model.EventShort

class EventPagerAdapter(private val events: List<EventShort>, private val context: Context) : RecyclerView.Adapter<EventPagerAdapter.EventViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val composeView = ComposeView(context).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
        }
        return EventViewHolder(composeView)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        holder.composeView.setContent {
            EventCard(event = events[position])
        }
    }

    override fun getItemCount(): Int = events.size

    class EventViewHolder(val composeView: ComposeView) : RecyclerView.ViewHolder(composeView)
}
