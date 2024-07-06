package com.technobrain.projet42.ui.register


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import com.technobrain.projet42.ui.register.RegisterState
import com.technobrain.projet42.ui.login.LoginViewModel
import org.w3c.dom.Text

@Composable
fun RegisterScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    val viewModel: RegisterViewModel = viewModel()

    val viewModelState by viewModel.uiState.collectAsState()

    var showAlert by remember { mutableStateOf(false) }

    var textAlert by remember { mutableStateOf("") }

    LaunchedEffect(viewModelState) {
        when (val state = viewModelState) {
            is RegisterState.Loaded -> {
                navController.navigate("loginForm")
            }
            is RegisterState.Error -> {
                showAlert = true
                textAlert = "Erreur lors de l'inscription"
            }
            is RegisterState.Empty -> {

            }
            is RegisterState.Loading -> {
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
            title = { Text("Erreur lors de l'inscription") },

            text = { Text(textAlert) }
        )
    }


    if (viewModelState is RegisterState.Loading || viewModelState is RegisterState.Loaded) {
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
        val scrollState = rememberScrollState()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            var email by remember { mutableStateOf("") }
            var firstname by remember { mutableStateOf("") }
            var lastname by remember { mutableStateOf("") }
            var username by remember { mutableStateOf("") }
            var password by remember { mutableStateOf("") }
            var confirmPassword by remember { mutableStateOf("") }

            Text(
                text = "S'inscrire",
                fontSize = 24.sp,
                color = Color.Black,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Text(
                text = "Bienvenue ! Créez votre compte.",
                fontSize = 16.sp,
                color = Color.Gray,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Adresse email") },
                placeholder = { Text("Adresse email") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                shape = RoundedCornerShape(14.dp)
            )

            OutlinedTextField(
                value = firstname,
                onValueChange = { firstname = it },
                label = { Text("Prénom") },
                placeholder = { Text("Prénom") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                shape = RoundedCornerShape(14.dp)
            )

            OutlinedTextField(
                value = lastname,
                onValueChange = { lastname = it },
                label = { Text("Nom") },
                placeholder = { Text("Nom") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                shape = RoundedCornerShape(14.dp)
            )

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
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
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

            var isConfirmPasswordVisible by remember { mutableStateOf(false) }


            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Confirmation du mot de passe") },
                placeholder = { Text("Confirmation du mot de passe") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                shape = RoundedCornerShape(14.dp),
                visualTransformation = if (isConfirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    val image =
                        if (isConfirmPasswordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff

                    IconButton(onClick = {
                        isConfirmPasswordVisible = !isConfirmPasswordVisible
                    }) {
                        Icon(imageVector = image, contentDescription = null)
                    }
                }
            )

            Button(
                onClick = {
                    if(email.isEmpty() || firstname.isEmpty() || lastname.isEmpty() || username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()){
                        showAlert = true
                        textAlert = "Veuillez remplir tous les champs"
                    }
                    else if(password != confirmPassword){
                        showAlert = true
                        textAlert = "Les mots de passe ne correspondent pas"
                    }
                    else{
                        viewModel.submitRegister(username, password, email, firstname, lastname)
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors( Color(0xFF1B85F3)),
            ){
                Text(text = "Créer son compte")
            }


            TextButton(
                onClick = {
                    navController.navigate("loginForm")
                },
                modifier = Modifier.padding(top = 24.dp)
            ) {
                Text(text = "Vous êtes déjà membre ? Se connecter", color = Color(0xFF1B85F3))
            }
        }
    }
}

@Composable
@Preview
fun LoginFormPreview() {
    RegisterScreen(navController = rememberNavController()  )
}