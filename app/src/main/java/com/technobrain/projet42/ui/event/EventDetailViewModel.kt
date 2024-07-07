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
    private val _parcoursJSON = MutableStateFlow<String>("")  // Add this line

    val uiState: MutableStateFlow<EventDetailState> get() = _uiState
    val parcoursJSON: MutableStateFlow<String> get() = _parcoursJSON  // Add this line

    fun getEventDetail(eventId: String) {
        viewModelScope.launch {
            _uiState.update {
                EventDetailState.Loading
            }

            apiRepository.getEventDetail(eventId).onSuccess { event ->
                _uiState.update {
                    EventDetailState.Loaded(event)
                }
                _parcoursJSON.value = event.parcoursJSON  // Add this line
            }.onFailure { error ->
                _uiState.update {
                    EventDetailState.Error(error.message ?: "An error occurred")
                }
            }
        }
    }
}