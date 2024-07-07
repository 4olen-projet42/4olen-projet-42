package com.technobrain.projet42.data.api;

import com.technobrain.projet42.data.api.model.DocumentResponse
import com.technobrain.projet42.data.api.model.EventDetailResponse
import com.technobrain.projet42.data.api.model.EventResponse
import com.technobrain.projet42.data.api.model.StatsResponse
import com.technobrain.projet42.data.api.model.UserResponse
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface Projet42API {

    @GET("/api/utilisateurs/me")
    suspend fun userInfos(
        @Header("Authorization") token: String,
    ): Response<UserResponse>

    @Multipart
    @POST("/api/documents/upload")
    suspend fun uploadFile(
        @Part file: MultipartBody.Part,
        @Header("Authorization") token: String
    ): Response<ResponseBody>

    @GET("/api/documents")
    suspend fun getDocuments(
        @Header("Authorization") token: String,
    ): Response<List<DocumentResponse>>

    @DELETE("/api/documents/{id}")
    suspend fun deleteDocument(
        @Header("Authorization") token: String,
        @Path("id") documentId: String
    ): Response<ResponseBody>

    @GET("/api/evenements/byUser")
    suspend fun userEvents(
        @Header("Authorization") token: String,
    ): Response<List<EventResponse>>

    @GET("/api/evenements/statsByUser")
    suspend fun userStats(
        @Header("Authorization") token: String,
    ): Response<StatsResponse>

    @GET("/evenements/like/{search}")
    suspend fun searchEvent(
        @Path("search") search: String
    ): Response<List<EventResponse>>

    @GET("/evenements/allAvailable")
    suspend fun getEvents(
    ): Response<List<EventResponse>>

    // getEventDetail
    @GET("/evenements/{id}")
    suspend fun getEventDetail(
        @Path("id") id: String
    ): Response<EventDetailResponse>


}
