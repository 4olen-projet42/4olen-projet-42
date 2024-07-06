package com.technobrain.projet42.ui.user

import com.technobrain.projet42.domain.model.EventShort
import com.technobrain.projet42.domain.model.StatShort
import com.technobrain.projet42.domain.model.UserShort

sealed class UserAccountState {

    data object Loading : UserAccountState()

    data class Loaded(val userShort: UserShort, val events: List<EventShort>, val statShort: StatShort) : UserAccountState()

    data class Error(val message: String) : UserAccountState()
}