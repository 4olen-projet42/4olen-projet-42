package com.technobrain.projet42.ui.user

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.technobrain.projet42.data.Projet42Repository
import com.technobrain.projet42.domain.repositories.ApiRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class UserAccountViewModel (application: Application) : AndroidViewModel(application) {

    private val userRepository: ApiRepository = Projet42Repository(application)

    private val _uiState = MutableStateFlow<UserAccountState>(UserAccountState.Loading)

    val uiState: StateFlow<UserAccountState> get() = _uiState.asStateFlow()

    fun fetchUserInfos() {
        viewModelScope.launch {
            _uiState.update {
                UserAccountState.Loading
            }
            userRepository.getUserInfos().onSuccess { user ->
                _uiState.update {
                    UserAccountState.Loaded(user)
                }
            }.onFailure { error ->
                _uiState.update {
                    UserAccountState.Error(error.message ?: "An error occurred")
                }
            }
        }
    }
}