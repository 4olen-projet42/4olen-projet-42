package com.technobrain.projet42.ui.document

import com.technobrain.projet42.domain.model.DocumentShort

sealed class DocumentState {

    data object Empty : DocumentState()

    data object Loading : DocumentState()

    data class Loaded(val documents: List<DocumentShort>) : DocumentState()

    data class Error(val message: String) : DocumentState()
}