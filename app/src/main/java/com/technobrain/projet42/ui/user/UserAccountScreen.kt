package com.technobrain.projet42.ui.user

import androidx.compose.foundation.background
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.technobrain.projet42.R
import com.technobrain.projet42.data.api.SessionManager
import com.technobrain.projet42.ui.component.UserInfo
import com.technobrain.projet42.domain.model.StatShort
import com.technobrain.projet42.ui.component.UserListEvent

@Composable
fun UserAccountScreen(
    stat: StatShort,
    navController: NavHostController,
    sessionManager: SessionManager,
    modifier: Modifier = Modifier,
) {
    var selectedTab by remember { mutableStateOf(0) }
    val tabTitles = listOf("Informations", "Evènements")

    val viewModel: UserAccountViewModel = viewModel()
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
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
            val events = (uiState as UserAccountState.Loaded).events
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
                        .fillMaxSize()
                        .background( Color(red = 66, green = 167, blue = 245) ),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    AsyncImage(
                        modifier = Modifier
                            .size(100.dp)
                            .clip(CircleShape),
                        model = user.photoUrl,
                        contentScale = ContentScale.Crop,
                        contentDescription = "Photo de profil",
                        placeholder = painterResource(R.drawable.ic_launcher_foreground)
                    )
                    Spacer(modifier = modifier.size(30.dp))
                    Row {
                        Text(
                            user.prenom + " " + user.nom,
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                    Spacer(modifier = modifier.size(50.dp))
                    TabRow(
                        selectedTabIndex = selectedTab,
                        containerColor = Color(red = 246, green = 246, blue = 246),
                        contentColor = Color.White,
                        modifier = Modifier.fillMaxWidth(),
                        indicator = { tabPositions ->
                            SecondaryIndicator(
                                Modifier.tabIndicatorOffset(tabPositions[selectedTab]),
                                height = 4.dp,
                                color = Color(red = 66, green = 167, blue = 245)
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
                                        style = MaterialTheme.typography.titleLarge.copy(
                                            fontWeight = if (selectedTab == index) FontWeight.Bold else FontWeight.Normal,
                                            color = if (selectedTab == index) Color(red = 66, green = 167, blue = 245) else Color(red = 189, green = 189, blue = 189)
                                        )
                                    )
                                }
                            )
                        }
                    }
                    Box(modifier = modifier
                        .fillMaxSize()
                        .background( Color.White )
                    ) {
                        when (selectedTab) {
                            0 -> UserInfo(navController, user, stat)
                            1 -> UserListEvent(events)
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