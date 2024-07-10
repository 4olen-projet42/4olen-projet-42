package com.technobrain.projet42.ui.home

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

class HomeEventViewModel(application: Application) : AndroidViewModel(application) {

    private val apiRepository: ApiRepository = Projet42Repository(application)

    private val _allEventsState = MutableStateFlow<HomeEventState>(HomeEventState.Loading)
    val allEventsState: StateFlow<HomeEventState> get() = _allEventsState.asStateFlow()

    private val _newEventsState = MutableStateFlow<HomeEventState>(HomeEventState.Loading)
    val newEventsState: StateFlow<HomeEventState> get() = _newEventsState.asStateFlow()

    fun searchEvent(text: String) {
        viewModelScope.launch {
            _allEventsState.update { HomeEventState.Loading }
            apiRepository.searchEvent(text).onSuccess { event ->
                _allEventsState.update { HomeEventState.Loaded(event) }
            }.onFailure { error ->
                _allEventsState.update { HomeEventState.Error(error.message ?: "An error occurred") }
            }
        }
    }

    fun getEvents() {
        viewModelScope.launch {
            _allEventsState.update { HomeEventState.Loading }
            apiRepository.getEvents().onSuccess { events ->
                _allEventsState.update { HomeEventState.Loaded(events) }
            }.onFailure { error ->
                _allEventsState.update { HomeEventState.Error(error.message ?: "An error occurred") }
            }
        }
    }

    fun getNewEvents() {
        viewModelScope.launch {
            _newEventsState.update { HomeEventState.Loading }
            apiRepository.getNewEvents().onSuccess { events ->
                _newEventsState.update { HomeEventState.Loaded(events) }
            }.onFailure { error ->
                _newEventsState.update { HomeEventState.Error(error.message ?: "An error occurred") }
            }
        }
    }
}
