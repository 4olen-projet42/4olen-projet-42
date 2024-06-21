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
import androidx.navigation.NavHostController
import com.technobrain.projet42.domain.model.EventShort
import com.technobrain.projet42.ui.component.CarouselView
import com.technobrain.projet42.ui.component.EventsList
import com.technobrain.projet42.ui.component.SearchBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier.background(Red),
) {
    val Carouselevents = remember {
        mutableStateListOf(
            EventShort("1","Marathon", "Lun, 03 Juin", "Lyon"),
            EventShort("2","Concert", "Mar, 04 Juin", "Lyon"),
            EventShort("3","Festival", "Mer, 05 Juin", "Lyon"),
        )
    }
    val events = remember {
        mutableStateListOf(
            EventShort("1","Marathon", "Lun, 03 Juin", "Lyon"),
            EventShort("2","Concert", "Mar, 04 Juin", "Lyon"),
            EventShort("3","Festival", "Mer, 05 Juin", "Lyon"),
            EventShort("4","Exposition", "Jeu, 06 Juin", "Lyon"),
            EventShort("5","Conférence", "Ven, 07 Juin", "Lyon")
        )
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Home page", fontSize = 18.sp, fontWeight = FontWeight.Bold) },
                actions = {
                    IconButton(
                        onClick = { navController.navigate("userAccountPage") }
                    ) {
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
                CarouselView(events = Carouselevents, context = LocalContext.current)
                Spacer(modifier = Modifier.padding(8.dp))
                EventsList(events = events)

            }
        }
    )
}



@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    EventScreen(navController = NavHostController(LocalContext.current))
}