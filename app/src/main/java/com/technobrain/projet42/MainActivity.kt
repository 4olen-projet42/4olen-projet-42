package com.technobrain.projet42

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
import com.technobrain.projet42.domain.model.StatShort
import com.technobrain.projet42.ui.login.LoginForm
import com.technobrain.projet42.ui.event.EventScreen
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

                val sessionManager = SessionManager(this)
                AppNavigator(sessionManager)

            }
        }
    }
}


@Composable
fun AppNavigator(sessionManager: SessionManager) {
    val navController = rememberNavController()

    NavHost(navController, startDestination = "eventScreen") {
        composable("eventScreen") { EventScreen(navController, sessionManager) }
        composable("userAccountPage") { UserAccountScreen(
            StatShort(42.0, 42.5, 42, 35.3),
            navController,
            sessionManager
        ) }
        composable("LoginForm") { LoginForm(navController) }
        composable("registerScreen") { RegisterScreen(navController) }
    }
}

