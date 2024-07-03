package com.technobrain.projet42.domain.repositories;

import com.technobrain.projet42.domain.model.DocumentShort
import com.technobrain.projet42.domain.model.EventShort
import com.technobrain.projet42.domain.model.UserShort
import java.io.File

interface ApiRepository {

    suspend fun getUserInfos(): Result<UserShort>


    suspend fun uploadFile(path: String, name: String): Result<String>

    suspend fun getDocuments(): Result<List<DocumentShort>>

    suspend fun deleteDocument(documentId: String): Result<String>

    suspend fun getUserEvents(): Result<List<EventShort>>

}
