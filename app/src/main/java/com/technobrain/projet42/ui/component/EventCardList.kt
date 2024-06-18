package com.technobrain.projet42.ui.component


import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.technobrain.projet42.domain.model.Event

@Composable
fun EventsList(events: List<Event>) {
    LazyColumn(modifier = Modifier.padding(horizontal = 16.dp)) {
        items(events) { event ->
            EventItem(event)
        }
    }
}


@Preview
@Composable
fun EventCardListPreview() {
    val events = listOf(
        Event("Marathon", "Lun, 03 Juin"),
        Event("Concert", "Mar, 04 Juin"),
        Event("Festival", "Mer, 05 Juin"),
        Event("Exposition", "Jeu, 06 Juin"),
        Event("Conférence", "Ven, 07 Juin"),
    )
    EventsList(events)
}
