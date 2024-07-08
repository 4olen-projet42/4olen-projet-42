package com.technobrain.projet42.ui.event

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.technobrain.projet42.data.Projet42Repository
import com.technobrain.projet42.domain.repositories.ApiRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class EventDetailViewModel(application: Application) : AndroidViewModel(application) {
    private val apiRepository: ApiRepository = Projet42Repository(application)

    private val _uiState = MutableStateFlow<EventDetailState>(EventDetailState.Loading)

    val uiState: MutableStateFlow<EventDetailState> get() = _uiState

    fun getEventDetail(eventId: String) {
        viewModelScope.launch {
            _uiState.update {
                EventDetailState.Loading
            }

            apiRepository.getEventDetail(eventId).onSuccess { event ->
                _uiState.update {
                    EventDetailState.Loaded(event)
                }
            }.onFailure { error ->
                _uiState.update {
                    EventDetailState.Error(error.message ?: "An error occurred")
                }
            }
        }
    }
}