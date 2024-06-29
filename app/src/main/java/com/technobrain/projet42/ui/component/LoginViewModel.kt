package com.technobrain.projet42.ui.component

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.technobrain.projet42.data.KeycloakRepository
import com.technobrain.projet42.domain.repositories.LoginRepository
import kotlinx.coroutines.launch
import kotlin.math.log
import android.content.Context

class LoginViewModel(context: Context) : ViewModel() {

    private val loginRepository: LoginRepository = KeycloakRepository(context)

    var username: String = ""
    var password: String = ""

    fun onUsernameChange(newUsername: String) {
        username = newUsername
    }

    fun onPasswordChange(newPassword: String) {
        password = newPassword
    }

    fun submitLogin() {
        viewModelScope.launch {
            try {
                val message = loginRepository.login(username, password)
                Log.d("LoginViewModel", message)

                val storedToken = loginRepository.getAccessToken()
                println("Stored Access Token: $storedToken")

            } catch (e: Exception) {
                Log.e("LoginViewModel", "Error logging in", e)
            }
        }


    }
}
