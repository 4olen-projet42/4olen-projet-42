package com.technobrain.projet42

import EventDetailScreen
import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.technobrain.projet42.data.api.SessionManager
import com.technobrain.projet42.domain.model.EventShort
import com.technobrain.projet42.domain.model.StatShort
import com.technobrain.projet42.ui.document.DocumentScreen
import com.technobrain.projet42.ui.event.EventScreen
import com.technobrain.projet42.ui.event.MyMapPage
import com.technobrain.projet42.ui.login.LoginForm
import com.technobrain.projet42.ui.register.RegisterScreen
import com.technobrain.projet42.ui.user.UserAccountScreen


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {

                AppNavigator(this)

            }
        }
    }
}


@Composable
fun AppNavigator(context: Context) {
    val sessionManager = SessionManager(context)

    val events = listOf(
        EventShort("1","Marathon", "Lun, 03 Juin", "Lyon", "1Okm"),
        EventShort("2","Concert", "Mar, 04 Juin", "Lyon", "1Okm"),
        EventShort("3","Festival", "Mer, 05 Juin", "Lyon", "1Okm"),
        EventShort("4","Exposition", "Jeu, 06 Juin", "Lyon", "1Okm"),
        EventShort("5","Conférence", "Ven, 07 Juin", "Lyon", "1Okm")
    )

    val navController = rememberNavController()

    NavHost(navController, startDestination = "eventScreen") {
        composable("eventScreen") { EventScreen(navController, sessionManager) }
        composable("mapScreen") { MyMapPage() }
        composable("userAccountPage") { UserAccountScreen(
            StatShort(42.0, 42.5, 42, 35.3),
            navController,
            sessionManager,
            context
        ) }
        composable("LoginForm") { LoginForm(navController) }
        composable("registerScreen") { RegisterScreen(navController) }
        composable("eventDetail/{eventId}") { backStackEntry ->
            val eventId = backStackEntry.arguments?.getString("eventId")
            val event = events.find { it.id == eventId }
            if (event != null) {
                EventDetailScreen(event, navController)
            }
        }
        composable("documentScreen") { DocumentScreen(context) }
    }
}

