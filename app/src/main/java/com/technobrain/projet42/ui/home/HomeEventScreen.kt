package com.technobrain.projet42.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.technobrain.projet42.data.api.SessionManager
import com.technobrain.projet42.ui.component.CarouselView
import com.technobrain.projet42.ui.component.EventsList
import com.technobrain.projet42.ui.component.SearchBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventScreen(
    navController: NavHostController,
    sessionManager: SessionManager,
) {

    val viewModel: HomeEventViewModel = viewModel()

    // Collect states for new events and all events
    //val newEventsState by viewModel.newEventsState.collectAsState()
    val allEventsState by viewModel.allEventsState.collectAsState()

    // Initialize both states
    LaunchedEffect(Unit) {
        viewModel.getEvents()
    }

    Scaffold(topBar = {
        TopAppBar(title = {
            Text(
                "Page d'accueil", fontSize = 18.sp, fontWeight = FontWeight.Bold
            )
        }, actions = {
            IconButton(onClick = {
                if (sessionManager.isUserLoggedIn()) {
                    navController.navigate("userAccountPage")
                } else {
                    navController.navigate("loginForm")
                }
            }

            ) {
                Icon(Icons.Filled.Person, contentDescription = "Profile")
            }

        })
    }, content = { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            SearchBar(onSearchClick = { viewModel.searchEvent(it) })
            Spacer(modifier = Modifier.padding(8.dp))

            when (val state = allEventsState) {
                is HomeEventState.Loading -> CircularProgressIndicator()
                is HomeEventState.Loaded -> {
                    CarouselView(events = state.newEvents, context = LocalContext.current, navController = navController)
                    EventsList(events = state.events, navController = navController)
                }
                is HomeEventState.Error -> Text(text = state.message)
                HomeEventState.Empty -> Text(text = "No events available")
            }

        }
    })
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    EventScreen(
        navController = NavHostController(LocalContext.current),
        sessionManager = SessionManager(LocalContext.current)
    )

}