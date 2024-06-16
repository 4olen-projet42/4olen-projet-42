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
import com.technobrain.projet42.ui.component.LoginForm
import com.technobrain.projet42.ui.component.LoginViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                LoginFormApp()
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {

}

@Composable
fun LoginFormApp() {
    val loginViewModel: LoginViewModel = viewModel()

    LoginForm(
        submit = { loginViewModel.submitLogin() }
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    LoginFormApp()
}