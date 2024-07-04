package com.technobrain.projet42.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.technobrain.projet42.R
import com.technobrain.projet42.domain.model.Event
import com.technobrain.projet42.domain.model.EventShort

@Composable
fun EventItem(event: EventShort, navController: NavHostController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { navController.navigate("eventDetail/${event.id}") },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image( // change to AsyncImage when available
            painter = painterResource( id = R.drawable.baseline_run_circle_24),
            contentDescription = event.name,
            modifier = Modifier
                .size(64.dp)
                .clip(CircleShape)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(text = event.name, style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
            Row (verticalAlignment = Alignment.CenterVertically)
            {
                Text(text = event.location, style = MaterialTheme.typography.bodyMedium)
                Icon(Icons.Filled.LocationOn, contentDescription = "Location", tint = Color.Red, modifier = Modifier.size(16.dp))
            }
                Text(text = event.date, style = MaterialTheme.typography.bodyMedium)

        }
    }
}

@Preview
@Composable
fun EventItemPreview() {
    MaterialTheme {
        /*EventItem(
            EventShort("1","Marathon", "Lun, 03 Juin", "Lyon")
        )*/
    }
}
