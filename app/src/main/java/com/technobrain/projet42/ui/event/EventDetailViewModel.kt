package com.technobrain.projet42.ui.event

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.technobrain.projet42.data.Projet42Repository
import com.technobrain.projet42.domain.model.EventDetail
import com.technobrain.projet42.domain.model.Inscription
import com.technobrain.projet42.domain.repositories.ApiRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.random.Random

class EventDetailViewModel(application: Application) : AndroidViewModel(application) {
    private val apiRepository: ApiRepository = Projet42Repository(application)

    private val _uiState = MutableStateFlow<EventDetailState>(EventDetailState.Loading)

    val uiState: MutableStateFlow<EventDetailState> get() = _uiState

    fun createInscription(event: EventDetail) {
        viewModelScope.launch {
            val eventId = event.id
            val eventNom = event.nom
            val img = event.image
            val maxParticipants = event.maxParticipants
            val date = event.date
            val location = event.location
            val distance = event.distance
            val parcoursJSON = event.parcoursJSON
            val denivele = event.denivele
            val heure = event.heure

            val inscription = Inscription(
                Random.nextLong(),
                1,
                EventDetail(
                    eventId,
                    eventNom,
                    img,
                    maxParticipants,
                    date,
                    location,
                    distance,
                    parcoursJSON,
                    denivele,
                    heure,
                    emptyList()
                )
            )

            apiRepository.createOrUpdateInscription(inscription).onSuccess {
                _uiState.update {
                    EventDetailState.InscriptionCreated
                }
            }.onFailure { error ->
                _uiState.update {
                    EventDetailState.Error(error.message ?: "An error occurred")
                }
            }
        }
    }

    fun getEventDetail(eventId: String) {
        viewModelScope.launch {
            _uiState.update {
                EventDetailState.Loading
            }

            apiRepository.getEventDetail(eventId).onSuccess { event ->
                _uiState.update {
                    EventDetailState.Loaded(event = event)
                }
            }.onFailure { error ->
                _uiState.update {
                    EventDetailState.Error(error.message ?: "An error occurred")
                }
            }
        }
    }

}