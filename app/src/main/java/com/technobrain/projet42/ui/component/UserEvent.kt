package com.technobrain.projet42.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.technobrain.projet42.domain.model.EventShort

@Composable
fun UserEvent(
    eventShort: EventShort,
    modifier: Modifier = Modifier
){
    Card(
        modifier = modifier
            .height(75.dp)
            .clip(RoundedCornerShape(10.dp))
    ){
        Row(
            modifier = modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = modifier.size(10.dp))
            Column {
                Text(
                    text = eventShort.name,
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                )
                Spacer(modifier.size(10.dp))
                Text(
                    text = eventShort.date,
                    style = MaterialTheme.typography.titleLarge.copy(fontStyle = FontStyle.Italic)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun UserEventPreview() {
    MaterialTheme {
        val event = EventShort(
            id = "1",
            name = "Evenement 1",
            date = "Lundi 01 janvier",
            location = "Lyon",
            distance = "10 km"
        )
        UserEvent(event)
    }
}