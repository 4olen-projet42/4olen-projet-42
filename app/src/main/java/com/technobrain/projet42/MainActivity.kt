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
import com.technobrain.projet42.ui.document.DocumentScreen
import com.technobrain.projet42.ui.home.EventScreen
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
    val navController = rememberNavController()

    NavHost(navController, startDestination = "eventScreen") {
        composable("eventScreen") { EventScreen(navController, sessionManager) }
        composable("userAccountPage") {
            UserAccountScreen(
                navController,
                sessionManager,
                context
            )
        }
        composable("LoginForm") { LoginForm(navController) }
        composable("registerScreen") { RegisterScreen(navController) }
        composable("eventDetail/{eventId}") { backStackEntry ->
            val eventId = backStackEntry.arguments?.getString("eventId")
            EventDetailScreen(eventId!!, navController)
        }
        composable("documentScreen") { DocumentScreen(context) }
    }
}

