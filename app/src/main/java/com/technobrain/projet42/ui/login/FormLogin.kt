package com.technobrain.projet42.ui.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp

@Composable
fun LoginForm(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    val viewModel: LoginViewModel = viewModel()

    val viewModelState by viewModel.uiState.collectAsState()

    var showAlert by remember { mutableStateOf(false) }

    var textAlert by remember { mutableStateOf("") }

    LaunchedEffect(viewModelState) {
        when (val state = viewModelState) {
            is LoginState.Loaded -> {
                navController.navigate("userAccountPage")
            }
            is LoginState.Error -> {
                showAlert = true
                textAlert = "Nom d'utilisateur ou mot de passe incorrect"
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
            text = { Text(textAlert) }
        )
    }

    if (viewModelState is LoginState.Loading || viewModelState is LoginState.Loaded) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator()
        }
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            //verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            IconButton(
                onClick = { navController.navigate("eventScreen") },
                modifier = Modifier.align(Alignment.Start)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Retour"
                )
            }

            Text(
                text = "Connexion",
                fontSize = 24.sp,
                color = Color.Black,
                modifier = Modifier.padding(bottom = 16.dp)
            )


            var username by remember { mutableStateOf("") }
            var password by remember { mutableStateOf("") }


            OutlinedTextField(
                value = username,
                onValueChange = { username = it },
                label = { Text("Nom d'utilisateur") },
                placeholder = { Text("Nom d'utilisateur") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                shape = RoundedCornerShape(14.dp)
            )

            var isPasswordVisible by remember { mutableStateOf(false) }


            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Mot de passe") },
                placeholder = { Text("Mot de passe") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                shape = RoundedCornerShape(14.dp),
                visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    val image =
                        if (isPasswordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff

                    IconButton(onClick = {
                        isPasswordVisible = !isPasswordVisible
                    }) {
                        Icon(imageVector = image, contentDescription = null)
                    }
                }
            )

            Button(
                onClick = {
                    if(username.isEmpty() || password.isEmpty()){
                        showAlert = true
                        textAlert = "Veuillez remplir tous les champs"
                    }
                    else
                    {
                        viewModel.submitLogin(username, password)
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors( Color(0xFF1B85F3)),
            ){
                Text(text = "Connexion")
            }

            TextButton(
                onClick = {
                    navController.navigate("registerScreen")
                },
                modifier = Modifier.padding(top = 24.dp)
            ) {
                Text(text = "Première fois ? Inscrivez-vous", color = Color(0xFF1B85F3))
            }
        }
    }
}

@Composable
@Preview
fun LoginFormPreview() {
    LoginForm(navController = rememberNavController()  )
}