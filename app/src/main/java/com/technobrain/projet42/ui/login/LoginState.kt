package com.technobrain.projet42.ui.login

sealed class LoginState {

    data object Empty : LoginState()

    data object Loading : LoginState()

    data class Loaded(val token: String) : LoginState()

    data class Error(val message: String) : LoginState()
}