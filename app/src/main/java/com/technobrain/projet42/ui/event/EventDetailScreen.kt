import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.technobrain.projet42.ui.component.OsmMap
import com.technobrain.projet42.ui.event.EventDetailState
import com.technobrain.projet42.ui.event.EventDetailViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventDetailScreen(
    eventId: String,
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    val viewModel: EventDetailViewModel = viewModel()

    val viewModelState by viewModel.uiState.collectAsState()
    // val parcoursJSON by viewModel.parcoursJSON.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getEventDetail(eventId)
    }
    when (val state = viewModelState) {
        is EventDetailState.Loaded -> {
            val event = state.event
            Scaffold(

                topBar = {
                    TopAppBar(
                        title = { Text(event.nom) },
                        navigationIcon = {
                            IconButton(onClick = { navController.navigate("eventScreen") }) {
                                Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                            }
                        },
                    )
                }
            ) { innerPadding ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {


                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(250.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        OsmMap(event.parcoursJSON)
                    }

                    Card(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                if (event.distance != 0) {
                                    Text(event.distance.toString(), fontWeight = FontWeight.Bold)
                                    Text("Distance")
                                }
                            }
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text("1h 30min", fontWeight = FontWeight.Bold)
                                Text("temps")
                            }
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text("1240 m", fontWeight = FontWeight.Bold)
                                Text("Dénivelé")
                            }
                        }
                    }

                    // Date and Time
                    Card(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column {
                                Text("Heure de départ", fontWeight = FontWeight.Bold)
                                Text("11 h 00")
                            }
                            Column {
                                Text("Date", fontWeight = FontWeight.Bold)
                                Text("08/04/2024")
                            }
                        }
                    }

                    // Register Button
                    Button(
                        onClick = { /* Handle register action */ },
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        Text("S'INSCRIRE", color = Color.White)
                    }
                }


            }
        }

        EventDetailState.Empty -> {
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

        is EventDetailState.Error -> {
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

        EventDetailState.Loading -> {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Loading..."
                )
            }
        }
    }
}
