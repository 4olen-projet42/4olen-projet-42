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

class HomeEventViewModel (application: Application) : AndroidViewModel(application){

    private val apiRepository: ApiRepository = Projet42Repository(application)

    private val _uiState = MutableStateFlow<HomeEventState>(HomeEventState.Loading)

    val uiState: StateFlow<HomeEventState> get() = _uiState.asStateFlow()


    fun searchEvent(text: String) {

        // Create a new coroutine to move the execution off the UI thread
        viewModelScope.launch {
            _uiState.update {
                HomeEventState.Loading
            }

            apiRepository.searchEvent(text).onSuccess { event ->
                _uiState.update {
                    HomeEventState.Loaded(event)
                }
            }.onFailure { error ->
                _uiState.update {
                    HomeEventState.Error(error.message ?: "An error occurred")
                }
            }
        }
    }

    fun getEvents() {

        // Create a new coroutine to move the execution off the UI thread
        viewModelScope.launch {
            _uiState.update {
                HomeEventState.Loading
            }

            apiRepository.getEvents().onSuccess { event ->
                _uiState.update {
                    HomeEventState.Loaded(event)
                }
            }.onFailure { error ->
                _uiState.update {
                    HomeEventState.Error(error.message ?: "An error occurred")
                }
            }
        }
    }

    fun getNewEvents() {

        // Create a new coroutine to move the execution off the UI thread
        viewModelScope.launch {
            _uiState.update {
                HomeEventState.Loading
            }

            apiRepository.getNewEvents().onSuccess { event ->
                _uiState.update {
                    HomeEventState.Loaded(event)
                }
            }.onFailure { error ->
                _uiState.update {
                    HomeEventState.Error(error.message ?: "An error occurred")
                }
            }
        }
    }

}