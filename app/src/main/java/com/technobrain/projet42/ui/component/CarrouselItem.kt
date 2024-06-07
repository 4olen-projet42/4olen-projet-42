package com.technobrain.projet42.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.technobrain.projet42.R
import com.technobrain.projet42.domain.model.EventItem

@Composable
fun CarrouselItem(
    modifier: Modifier = Modifier,
    onItemClick: (movieId: String) -> Unit = {},
    event: EventItem,
) {
    Card(
        modifier = modifier
            .size(300.dp, 200.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_foreground),
                contentDescription = "",
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .aspectRatio(2.5f)
            )
            Column {

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {

                    Text(text = event.title, modifier = Modifier.padding(4.dp))
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                )
                {
                    Text(text = event.location)
                    Spacer(modifier = Modifier.size(10.dp))
                    Text(text = event.date)
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {

                    Text(text = "Participants max : ${event.nb_participants}", modifier = Modifier.padding(bottom = 5.dp))
                }
            }
        }
    }
}

@Composable
@Preview
fun CarrouselItemPreview() {
    CarrouselItem(
        event = EventItem(
            id = "1",
            title = "Marrathon de Lyon",
            posterUrl = "https://picsum.photos/200/300",
            date = "01/01/2025",
            description = "Description 1",
            location = "Lyon",
            nb_participants = 30
        )
    )
}
