package com.technobrain.projet42.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.technobrain.projet42.R
import com.technobrain.projet42.domain.model.EventItem

@Composable
fun EventItem(
    modifier: Modifier = Modifier, onItemClick: (eventId: String) -> Unit = {}, event: EventItem
) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_foreground),
                contentDescription = ""
            )

            Spacer(Modifier.size(20.dp))

            Column {
                Row {

                    Text(
                        text = event.title,
                    )
                    Spacer(Modifier.size(10.dp))
                    Text(
                        text = event.date,
                    )
                }
                Text(text = "Description : ${event.description}")

                Text(text = "Location : ${event.location}")

                Text(text = "Participants max: ${event.nb_participants}")
            }
        }
    }

}

@Preview
@Composable
fun EventItemPreview() {
    MaterialTheme {
        EventItem(
            event = EventItem(
                "1",
                "Event 1",
                "https://www.google.com",
                "2021-10-10",
                "Description",
                "Location",
                10
            )
        )
    }
}
