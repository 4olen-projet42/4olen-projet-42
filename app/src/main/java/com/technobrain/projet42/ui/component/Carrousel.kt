package com.technobrain.projet42.ui.component

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.technobrain.projet42.domain.model.EventItem

@Composable
fun CarouselComponent(
    events: List<EventItem>,
    onItemClick: (String) -> Unit
) {
    LazyRow {
        items(events) { event ->
            CarrouselItem(
                modifier = Modifier.padding(end = 16.dp),
                event = event,
                onItemClick = onItemClick
            )
        }
    }
}



@Composable
@Preview
fun DisplayCarousel() {
    // Example event items for demonstration
    val events = listOf(
        EventItem(id = "1", title = "Event 1", posterUrl = "https://picsum.photos/200/300", date = "01/01/2022", description = "Description 1", location = "Location 1", nb_participants = 10),
        EventItem(id = "2", title = "Event 2", posterUrl = "https://picsum.photos/200/300", date = "02/01/2022", description = "Description 2", location = "Location 2", nb_participants = 20),
        EventItem(id = "3", title = "Event 3", posterUrl = "https://picsum.photos/200/300", date = "03/01/2022", description = "Description 3", location = "Location 3", nb_participants = 30)
    )

    CarouselComponent(
        events = events,
        onItemClick = { /* Handle item click */ }
    )
}
