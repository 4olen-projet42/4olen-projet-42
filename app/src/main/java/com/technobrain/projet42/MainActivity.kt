package com.technobrain.projet42

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.technobrain.projet42.domain.model.EventShort
import com.technobrain.projet42.domain.model.UserShort
import com.technobrain.projet42.ui.event.EventScreen
import com.technobrain.projet42.ui.user.UserAccountScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {


            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                AppNavigator()
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Greeting("Android")
}


@Composable
fun AppNavigator() {

    val events = listOf(
        EventShort("1","Marathon", "Lun, 03 Juin", "Lyon"),
        EventShort("2","Concert", "Mar, 04 Juin", "Lyon"),
        EventShort("3","Festival", "Mer, 05 Juin", "Lyon"),
        EventShort("4","Exposition", "Jeu, 06 Juin", "Lyon"),
        EventShort("5","Conférence", "Ven, 07 Juin", "Lyon")
    )

    val navController = rememberNavController()

    NavHost(navController, startDestination = "eventScreen") {
        composable("eventScreen") { EventScreen(navController) }
        composable("userAccountPage") { UserAccountScreen(user = UserShort("1", "Doe", "John", "jdoe@mail.com","https://avatars.githubusercontent.com/u/117664928?v=4"), events = events) }
    }
}