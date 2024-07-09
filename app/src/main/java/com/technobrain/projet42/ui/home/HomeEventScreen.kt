package com.technobrain.projet42.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.technobrain.projet42.data.api.SessionManager
import com.technobrain.projet42.domain.model.EventShort
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

    val viewModelState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getEvents()
    }

    val Carouselevents = remember {
        mutableStateListOf(
            EventShort("1","Marathon", "Lun, 03 Juin", "Lyon", "10 km"),
            EventShort("2","Concert", "Mar, 04 Juin", "Lyon", "20 km"),
            EventShort("3","Festival", "Mer, 05 Juin", "Lyon", "30 km"),
        )
    }
    val events = remember {
        mutableStateListOf(
            EventShort("1","Marathon", "Lun, 03 Juin", "Lyon", "10 km"),
            EventShort("2","Concert", "Mar, 04 Juin", "Lyon", "20 km"),
            EventShort("3","Festival", "Mer, 05 Juin", "Lyon", "30 km"),
            EventShort("4","Exposition", "Jeu, 06 Juin", "Lyon", "40 km"),
            EventShort("5","Conférence", "Ven, 07 Juin", "Lyon", "50 km")
        )
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Page d'accueil", fontSize = 18.sp, fontWeight = FontWeight.Bold) },
                actions = {
                    IconButton(
                        onClick =
                        {
                            if (sessionManager.isUserLoggedIn()) {
                                navController.navigate("userAccountPage")
                            } else {
                                navController.navigate("loginForm")
                            }
                        }

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

                SearchBar(onSearchClick = { viewModel.searchEvent(it) })
                CarouselView(events = Carouselevents, context = LocalContext.current)
                Spacer(modifier = Modifier.padding(8.dp))
                when (val state = viewModelState) {
                    is HomeEventState.Empty -> {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "no result"
                            )
                        }
                    }

                    is HomeEventState.Error -> {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = state.message
                            )
                        }
                    }

                    is HomeEventState.Loaded -> {
                        EventsList(events = state.events, navController = navController)
                    }

                    is HomeEventState.Loading -> {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                }

            }
        }
    )
}



@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    EventScreen(navController = NavHostController(LocalContext.current), sessionManager = SessionManager(LocalContext.current))

}