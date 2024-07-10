package com.technobrain.projet42.ui.home

import com.technobrain.projet42.domain.model.EventShort


sealed class HomeEventState {

    data object Empty : HomeEventState()

    data object Loading : HomeEventState()

    data class Loaded(val events: List<EventShort>, val newEvents: List<EventShort>) : HomeEventState()
    data class Error(val message: String) : HomeEventState()
}