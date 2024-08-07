package com.technobrain.projet42.domain.repositories;

import com.technobrain.projet42.domain.model.DocumentShort
import com.technobrain.projet42.domain.model.EventDetail
import com.technobrain.projet42.domain.model.EventShort
import com.technobrain.projet42.domain.model.Inscription
import com.technobrain.projet42.domain.model.StatShort
import com.technobrain.projet42.domain.model.UserShort

interface ApiRepository {

    suspend fun getUserInfos(): Result<UserShort>

    suspend fun uploadFile(path: String, name: String): Result<String>

    suspend fun getDocuments(): Result<List<DocumentShort>>

    suspend fun deleteDocument(documentId: String): Result<String>

    suspend fun getUserEvents(): Result<List<EventShort>>

    suspend fun getUserStats(): Result<StatShort>
  
    suspend fun searchEvent(search: String): Result<List<EventShort>>

    suspend fun getEvents(): Result<List<EventShort>>

    suspend fun getEventDetail(eventId: String): Result<EventDetail>

    suspend fun getNewEvents(): Result<List<EventShort>>

    suspend fun createOrUpdateInscription(inscription: Inscription): Result<Inscription>
}
