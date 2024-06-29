package com.technobrain.projet42.ui.user

import com.technobrain.projet42.domain.model.UserShort

sealed class UserAccountState {

    data object Loading : UserAccountState()

    data class Loaded(val userShort: UserShort) : UserAccountState()

    data class Error(val message: String) : UserAccountState()
}