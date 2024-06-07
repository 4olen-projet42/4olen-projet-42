package com.technobrain.projet42.ui.event

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.technobrain.projet42.domain.model.EventItem
import com.technobrain.projet42.ui.component.CarouselComponent
import com.technobrain.projet42.ui.component.EventCardList

@Composable
fun HomeScreen(
    events: List<EventItem>,
    onItemClick: (String) -> Unit = {},
) {
    Column(modifier = Modifier.fillMaxSize().background(color = Color.White).padding(10.dp)) {
        // Carousel at the top of the screen
        CarouselComponent(
            events = events,
            onItemClick = onItemClick
        )

        Spacer(modifier = Modifier.size(20.dp))

        // List of events below the carousel
        EventCardList(
            events = events,
            modifier = Modifier,
            onItemClick = onItemClick
        )
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    val events = listOf(
        EventItem(id = "1", title = "Event 1", posterUrl = "https://picsum.photos/200/300", date = "01/01/2022", description = "Description 1", location = "Location 1", nb_participants = 10),
        EventItem(id = "2", title = "Event 2", posterUrl = "https://picsum.photos/200/300", date = "02/01/2022", description = "Description 2", location = "Location 2", nb_participants = 20),
        EventItem(id = "3", title = "Event 3", posterUrl = "https://picsum.photos/200/300", date = "03/01/2022", description = "Description 3", location = "Location 3", nb_participants = 30),
        EventItem(id = "4", title = "Event 4", posterUrl = "https://picsum.photos/200/300", date = "04/01/2022", description = "Description 4", location = "Location 4", nb_participants = 40),
        EventItem(id = "5", title = "Event 5", posterUrl = "https://picsum.photos/200/300", date = "05/01/2022", description = "Description 5", location = "Location 5", nb_participants = 50),
    )
    HomeScreen(events = events)
}
