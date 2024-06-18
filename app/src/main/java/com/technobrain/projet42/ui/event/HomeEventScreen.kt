package com.technobrain.projet42.ui.event

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.technobrain.projet42.R
import com.technobrain.projet42.domain.model.Event
import com.technobrain.projet42.ui.component.CarouselView
import com.technobrain.projet42.ui.component.EventsList
import com.technobrain.projet42.ui.component.SearchBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventScreen(
    modifier: Modifier = Modifier.background(Red)
) {
    val events = remember {
        mutableStateListOf(
            Event("Marathon", "Lun, 03 Juin"),
            Event("Concert", "Mar, 04 Juin"),
            Event("Festival", "Mer, 05 Juin"),
            Event("Exposition", "Jeu, 06 Juin"),
            Event("Conférence", "Ven, 07 Juin")
        )
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Home page", fontSize = 18.sp, fontWeight = FontWeight.Bold) },
                actions = {
                    IconButton(onClick = { /* Handle profile click */ }) {
                        Icon(Icons.Filled.Person, contentDescription = "Profile")
                    }

                }
            )
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                SearchBar()
                CarouselScreen()
                Spacer(modifier = Modifier.padding(8.dp))
                EventsList(events = events)

            }
        }
    )
}

@Composable
fun CarouselScreen() {
    val events = remember {
        mutableStateListOf(
            Event("Marathon", "Lun, 03 Juin"),
            Event("Concert", "Mar, 04 Juin"),
            Event("Festival", "Mer, 05 Juin"),
            Event("Exposition", "Jeu, 06 Juin"),
            Event("Conférence", "Ven, 07 Juin")
        )
    }
    CarouselView(events = events)
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    EventScreen()
}