package com.technobrain.projet42.ui.register

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
import com.technobrain.projet42.ui.register.RegisterState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class RegisterViewModel (application: Application) : AndroidViewModel(application) {
    private val loginRepository: LoginRepository = KeycloakRepository(application)

    private val _uiState = MutableStateFlow<RegisterState>(RegisterState.Empty)
    val uiState: StateFlow<RegisterState> get() = _uiState.asStateFlow()


    fun submitRegister(username: String, password: String, email: String, firstName: String, lastName: String) {
        viewModelScope.launch {
            _uiState.update {
                RegisterState.Loading
            }

            loginRepository.register(username, password, email, firstName, lastName).onSuccess {
                _uiState.update {
                    RegisterState.Loaded("Inscription réussie")
                }
            }.onFailure { error ->
                _uiState.update {
                    RegisterState.Error(error.message ?: "An error occurred")
                }
            }
        }
    }
}