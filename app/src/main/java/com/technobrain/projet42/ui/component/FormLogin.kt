package com.technobrain.projet42.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
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
import org.w3c.dom.Text

@Composable
fun LoginForm(
    modifier: Modifier = Modifier,
    submit: () -> Unit
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        var text by remember { mutableStateOf("") }

        TextField(
            value = text,
            onValueChange = { text = it },
            placeholder = { Text("Enter your Login") },
            label = { Text("Login") },
            singleLine = true,
            visualTransformation = VisualTransformation.None
        )

        var isPasswordVisible by remember { mutableStateOf(false) }
        var passwodText by remember { mutableStateOf("") }


        TextField(
            value = passwodText,
            onValueChange = { passwodText = it },
            placeholder = { Text("Enter your Password") },
            label = { Text("Password") },
            singleLine = true,
            // visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation()
        )

        Button(
            onClick = submit,
            modifier = Modifier.padding(16.dp)
        ) {
            Text("Submit")
        }
    }
}

@Composable
@Preview
fun LoginFormPreview() {
    LoginForm(
        submit = {}
    )
}