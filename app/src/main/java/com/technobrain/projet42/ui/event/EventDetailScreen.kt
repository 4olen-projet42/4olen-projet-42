import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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

    LaunchedEffect(Unit) {
        viewModel.getEventDetail(eventId)
    }

    when (val state = viewModelState) {
        is EventDetailState.Loaded -> {
            val event = state.event
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = { Text(event.nom, color = Color.White) },
                        navigationIcon = {
                            IconButton(onClick = { navController.navigate("eventScreen") }) {
                                Icon(
                                    Icons.Filled.ArrowBack,
                                    contentDescription = "Retour",
                                    tint = Color.White
                                )
                            }
                        },
                        colors = TopAppBarDefaults.smallTopAppBarColors(
                            containerColor = Color(
                                0xFF42A7F5
                            )
                        )
                    )
                }) { innerPadding ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .background(Color.White),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    OsmMap(
                        parcoursJSON = event.parcoursJSON,
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color(0xFFE0F7FA))
                            .padding(18.dp)
                    )

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight()
                            .background(Color.White),

                        ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(18.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Column {
                                    Text("Date", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                                    // format the date
                                    val date = event.date.split("-")
                                    Text("${date[2]}/${date[1]}/${date[0]}", fontSize = 18.sp)
                                }
                                Column {
                                    Text(
                                        "Heure de début",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 18.sp
                                    )
                                    Text(event.heure, fontSize = 18.sp)
                                }
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            if (event.sports.isNotEmpty()) {
                                Row {
                                    Text("Sport : ", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                                    for (sport in event.sports) {
                                        Text(
                                            text = sport + ", ",
                                            fontSize = 18.sp
                                        )
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            if (event.distance > 0) {
                                Row {

                                    Text(
                                        "Distance : ",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 18.sp
                                    )
                                    Text(
                                        text = event.distance.toString() + " km",
                                        fontSize = 18.sp
                                    )
                                }
                            }
                            if (event.denivele > 0) {
                                Row {
                                    Text(
                                        "Dénivelé : ",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 18.sp
                                    )
                                    Text(
                                        text = event.denivele.toString() + " m",
                                        fontSize = 18.sp
                                    )
                                }
                            }


                            Spacer(modifier = Modifier.height(18.dp))

                            Button(
                                onClick = { viewModel.createInscription(event) },
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(50),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color(0xFF42A7F5)
                                )
                            ) {
                                Text("S'INSCRIRE", color = Color.White)
                            }
                        }

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

        is EventDetailState.InscriptionCreated -> {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Inscription réussie !"
                )
            }
        }
    }
}