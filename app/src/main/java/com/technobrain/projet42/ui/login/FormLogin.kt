package com.technobrain.projet42.ui.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.constraintlayout.compose.Visibility
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import org.w3c.dom.Text

@Composable
fun LoginForm(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    val viewModel: LoginViewModel = viewModel()

    val viewModelState by viewModel.uiState.collectAsState()

    var showAlert by remember { mutableStateOf(false) }

    LaunchedEffect(viewModelState) {
        when (val state = viewModelState) {
            is LoginState.Loaded -> {
                navController.navigate("userAccountPage")
            }
            is LoginState.Error -> {
                showAlert = true
            }
            is LoginState.Empty -> {

            }
            is LoginState.Loading -> {
            }
        }
    }

    if (showAlert) {
        AlertDialog(
            onDismissRequest = { showAlert = false },
            confirmButton = {
                TextButton(onClick = { showAlert = false }) {
                    Text("OK")
                }
            },
            title = { Text("Erreur de connexion") },
            text = { Text("Nom d'utilisateur ou mot de passe incorrect") }
        )
    }

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {


        if (viewModelState is LoginState.Loading || viewModelState is LoginState.Loaded) {
            CircularProgressIndicator()
        } else {


            var username by remember { mutableStateOf("") }
            var password by remember { mutableStateOf("") }

            TextField(
                value = username,
                onValueChange = { username = it },
                placeholder = { Text("Nom d'utilisateur") },
                label = { Text("Nom d'utilisateur") },
                singleLine = true,
                visualTransformation = VisualTransformation.None
            )

            var isPasswordVisible by remember { mutableStateOf(false) }


            TextField(
                value = password,
                onValueChange = { password = it },
                placeholder = { Text("Mot de passe") },
                label = { Text("Mot de passe") },
                singleLine = true,
                visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    val image = if (isPasswordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff

                    IconButton(onClick = {
                        isPasswordVisible = !isPasswordVisible
                    }) {
                        Icon(imageVector = image, contentDescription = null)
                    }
                }
            )

            Button(
                onClick = { viewModel.submitLogin(username, password) },
                modifier = Modifier.padding(16.dp)
            ) {
                Text("Connexion")
            }
        }
    }
}

@Composable
@Preview
fun LoginFormPreview() {
    LoginForm(navController = rememberNavController()  )
}