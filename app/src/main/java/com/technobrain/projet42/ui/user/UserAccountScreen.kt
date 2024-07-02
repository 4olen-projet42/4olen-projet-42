package com.technobrain.projet42.ui.user

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.SecondaryIndicator
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.technobrain.projet42.R
import com.technobrain.projet42.data.api.SessionManager
import com.technobrain.projet42.ui.component.UserInfo
import com.technobrain.projet42.domain.model.EventShort
import com.technobrain.projet42.domain.model.StatShort
import com.technobrain.projet42.ui.component.EventsList

@Composable
fun UserAccountScreen(
    test: String,
    stat: StatShort,
    events: List<EventShort>,
    navController: NavHostController,
    sessionManager: SessionManager,
    modifier: Modifier = Modifier,
) {
    var selectedTab by remember { mutableStateOf(0) }
    val tabTitles = listOf("Informations", "Evènements")

    val viewModel: UserAccountViewModel = viewModel()
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(test) {
        viewModel.fetchUserInfos()
    }

    when (uiState) {
        is UserAccountState.Loading -> {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        is UserAccountState.Loaded -> {
            val user = (uiState as UserAccountState.Loaded).userShort
            Column(modifier = modifier.fillMaxSize()) {
                Box(
                    modifier = modifier
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    IconButton(
                        onClick = { navController.navigate("eventScreen") },
                        modifier = modifier.align(Alignment.TopStart)
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Retour"
                        )
                    }
                    Text(
                        "Profil",
                        style = MaterialTheme.typography.titleLarge
                    )
                    IconButton(
                        onClick = {
                            sessionManager.clearAccessToken()
                            navController.navigate("loginForm")
                        },
                        modifier = modifier.align(Alignment.TopEnd)
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                            contentDescription = "Déconnexion"
                        )
                    }
                }
                Column(
                    modifier = modifier
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    AsyncImage(
                        modifier = modifier
                            .size(100.dp)
                            .clip(CircleShape),
                        model = user.photoUrl,
                        contentScale = ContentScale.Crop,
                        contentDescription = "Photo de profil",
                        placeholder = painterResource(R.drawable.ic_launcher_foreground)
                    )
                    Spacer(modifier = modifier.size(30.dp))
                    Text(
                        user.nom,
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        user.prenom,
                        style = MaterialTheme.typography.titleMedium
                    )
                    Spacer(modifier = modifier.size(50.dp))
                    TabRow(
                        selectedTabIndex = selectedTab,
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                        modifier = Modifier.fillMaxWidth(),
                        indicator = { tabPositions ->
                            SecondaryIndicator(
                                Modifier.tabIndicatorOffset(tabPositions[selectedTab]),
                                height = 4.dp,
                                color = MaterialTheme.colorScheme.secondary
                            )
                        },
                        divider = {}
                    ) {
                        tabTitles.forEachIndexed { index, title ->
                            Tab(
                                selected = selectedTab == index,
                                onClick = { selectedTab = index },
                                modifier = Modifier.fillMaxWidth(0.5f),
                                text = {
                                    Text(
                                        title,
                                        style = MaterialTheme.typography.titleMedium.copy(
                                            fontWeight = if (selectedTab == index) FontWeight.Bold else FontWeight.Normal,
                                            color = if (selectedTab == index) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.onPrimaryContainer
                                        ),
                                        modifier = Modifier.padding(8.dp)
                                    )
                                }
                            )
                        }
                    }
                    Spacer(modifier = modifier.size(10.dp))
                    Box(modifier = modifier.fillMaxSize()) {
                        when (selectedTab) {
                            0 -> UserInfo(navController, user, stat)
                            1 -> EventsList(events)
                        }
                    }
                }
            }
        }
        is UserAccountState.Error -> {
            val errorMessage = (uiState as UserAccountState.Error).message
            Text(text = "Error: $errorMessage")
        }
    }


}

@Preview(showBackground = true)
@Composable
fun UserAccountPreview() {
    MaterialTheme {
        val stat = StatShort(
            distanceTotale = 100.0,
            tempsTotal = 10.0,
            nbParticipation = 5,
            classementMoyen = 3.0
        )
        val events = listOf(
            EventShort(
                id = "1",
                name = "Evenement 1",
                date = "Lundi 01 janvier",
                location = "Lyon"
            ),
            EventShort(
                id = "2",
                name = "Evenement 2",
                date = "Lundi 02 janvier",
                location = "Lyon"
            ),
            EventShort(
                id = "3",
                name = "Evenement 3",
                date = "Lundi 03 janvier",
                location = "Lyon"
            )
        )
        UserAccountScreen(
            test = "test",
            stat,
            events,
            navController = NavHostController(LocalContext.current),
            sessionManager = SessionManager(LocalContext.current)
        )
    }
}