package com.technobrain.projet42.ui.document

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


class DocumentViewModel (application: Application) : AndroidViewModel(application) {
    private val apiRepository: ApiRepository = Projet42Repository(application)

    private val _uiState = MutableStateFlow<DocumentState>(DocumentState.Loading)

    val uiState: StateFlow<DocumentState> get() = _uiState.asStateFlow()

    fun uploadFile(path: String, name: String) {
        viewModelScope.launch {
            apiRepository.uploadFile(path, name)
        }
    }

    fun getDocuments() {
        viewModelScope.launch {
            _uiState.update {
                DocumentState.Loading
            }
            apiRepository.getDocuments().onSuccess { documents ->
                _uiState.update {
                    DocumentState.Loaded(documents)
                }
            }.onFailure { error ->
                _uiState.update {
                    DocumentState.Error(error.message ?: "An error occurred")
                }
            }
        }
    }

    fun deleteDocument(documentId: String) {
        viewModelScope.launch {
            apiRepository.deleteDocument(documentId)
        }
    }

}