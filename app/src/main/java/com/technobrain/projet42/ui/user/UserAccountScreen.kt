package com.technobrain.projet42.ui.user

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.technobrain.projet42.R
import com.technobrain.projet42.ui.component.UserInfo
import com.technobrain.projet42.ui.component.UserListEvent
import com.technobrain.projet42.domain.model.UserShort
import com.technobrain.projet42.domain.model.EventShort

@Composable
fun UserAccountScreen(
    user: UserShort,
    events: List<EventShort>,
    modifier: Modifier = Modifier,
    onLogout: () -> Unit = {}
) {
    var selectedTab by remember { mutableStateOf(0) }
    Column(modifier = modifier.fillMaxSize()) {
        Box(
            modifier = modifier
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                "Profil",
                style = MaterialTheme.typography.titleLarge
            )
            IconButton(
                onClick = onLogout,
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
            Spacer(modifier = modifier.size(50.dp))
            TabRow(
                selectedTabIndex = selectedTab,
                ) {
                Tab(
                    selected = selectedTab == 0,
                    onClick = { selectedTab = 0 })
                {
                    Text("Mes informations",
                        style = MaterialTheme.typography.titleLarge
                    )
                }
                Tab(
                    selected = selectedTab == 1,
                    onClick = { selectedTab = 1 })
                {
                    Text("Mes évènements",
                        style = MaterialTheme.typography.titleLarge
                    )
                }
            }
            Spacer(modifier = modifier.size(10.dp))
            Box(modifier = modifier.fillMaxSize()) {
                when (selectedTab) {
                    0 -> UserInfo(user)
                    1 -> UserListEvent(events)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun UserAccountPreview() {
    MaterialTheme {
        val user = UserShort(
            id = "1",
            nom = "Nom",
            prenom = "Prenom",
            mail = "mail@mail.com",
            photoUrl = "https://avatars.githubusercontent.com/u/117666424?v=4"
        )
        val events = listOf(
            EventShort(
                id = "1",
                nom = "Evenement 1",
                date = "Lundi 01 janvier"
            ),
            EventShort(
                id = "2",
                nom = "Evenement 2",
                date = "Lundi 02 janvier"
            ),
            EventShort(
                id = "3",
                nom = "Evenement 3",
                date = "Lundi 03 janvier"
            )
        )
        UserAccountScreen(user, events)
    }
}