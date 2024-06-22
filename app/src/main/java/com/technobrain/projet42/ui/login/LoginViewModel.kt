package com.technobrain.projet42.ui.login

import android.app.Application
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
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    private val loginRepository: LoginRepository = KeycloakRepository(application)

    private val _uiState = MutableStateFlow<LoginState>(LoginState.Empty)
    val uiState: StateFlow<LoginState> get() = _uiState.asStateFlow()


    fun submitLogin(username: String, password: String) {

        viewModelScope.launch {
            _uiState.update {
                LoginState.Loading
            }

            loginRepository.login(username, password).onSuccess { token ->
                _uiState.update {
                    LoginState.Loaded(token)
                }
            }.onFailure { error ->
                _uiState.update {
                    LoginState.Error(error.message ?: "An error occurred")
                }
            }
        }
    }
}
