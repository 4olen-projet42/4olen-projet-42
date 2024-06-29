package com.technobrain.projet42.ui.register

sealed class RegisterState {

    data object Empty : RegisterState()

    data object Loading : RegisterState()

    data class Loaded(val success: String) : RegisterState()

    data class Error(val message: String) : RegisterState()
}