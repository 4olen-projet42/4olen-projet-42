package com.technobrain.projet42.ui.component


import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.technobrain.projet42.domain.model.EventShort

@Composable
fun EventsList(events: List<EventShort>, navController: NavHostController) {
    LazyColumn(modifier = Modifier.padding(horizontal = 16.dp)) {
        items(events) { event ->
            EventItem(event, navController)
        }
    }
}


@Preview
@Composable
fun EventCardListPreview() {
/*    val events = listOf(
        EventShort("1","Marathon", "Lun, 03 Juin", "Lyon", "10 km"),
        EventShort("2","Concert", "Mar, 04 Juin", "Lyon", "20 km"),
        EventShort("3","Festival", "Mer, 05 Juin", "Lyon", "30 km"),
        EventShort("4","Exposition", "Jeu, 06 Juin", "Lyon", "40 km"),
        EventShort("5","Conférence", "Ven, 07 Juin", "Lyon", "50 km")
    )*/
    // EventsList(events)
}
