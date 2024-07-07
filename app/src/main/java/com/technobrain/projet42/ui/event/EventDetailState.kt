package com.technobrain.projet42.ui.event

import com.technobrain.projet42.domain.model.EventDetail

sealed class EventDetailState {

    data object Empty : EventDetailState()

    data object Loading : EventDetailState()

    data class Loaded(val event: EventDetail) : EventDetailState()

    data class Error(val message: String) : EventDetailState()
}