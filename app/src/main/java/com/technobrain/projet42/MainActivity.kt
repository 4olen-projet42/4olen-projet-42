package com.technobrain.projet42

import android.content.Context
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
import com.technobrain.projet42.ui.component.LoginForm
import com.technobrain.projet42.ui.component.LoginViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.technobrain.projet42.ui.component.LoginViewModelFactory

class MainActivity : ComponentActivity() {

    private lateinit var loginViewModelFactory: LoginViewModelFactory
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                LoginFormApp(applicationContext)
            }
        }
    }
}

@Composable
fun LoginFormApp(context: Context) {
    val loginViewModel: LoginViewModel = viewModel(factory = LoginViewModelFactory(context))

    LoginForm(
        submit = { loginViewModel.submitLogin() }
    )
}

