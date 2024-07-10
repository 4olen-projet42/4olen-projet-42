package com.technobrain.projet42.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.technobrain.projet42.domain.model.EventShort

@Composable
fun EventCard(event: EventShort, onClick: () -> Unit) {
    Card(modifier = Modifier.fillMaxWidth(), onClick = { onClick() }) {
        AsyncImage(model = event.image, contentDescription = null, modifier = Modifier.aspectRatio(16f / 9).height(100.dp))
        Column(
            modifier = Modifier
                .background(Color(0xAA000000)) // Semi-transparent black background
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row {
                Text(text = event.name, color = Color.White, style = MaterialTheme.typography.bodyMedium)
                Text(text = ", ", color = Color.White, style = MaterialTheme.typography.bodyMedium)
                Text(text = event.location, color = Color.White, style = MaterialTheme.typography.bodyMedium)
            }
            Text(text = event.date, color = Color.White, style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@Preview
@Composable
fun EventCardPreview() {
    EventCard(
        EventShort(
            id = "1",
            name = "Evenement 1",
            date = "Lundi 01 janvier",
            location = "Lyon",
            distance = "10 km",
            image = "https://picsum.photos/200/300"
        )
    ) {
    }
}