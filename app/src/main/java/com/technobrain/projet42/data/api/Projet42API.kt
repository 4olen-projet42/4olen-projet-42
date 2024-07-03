package com.technobrain.projet42.data.api;

import com.technobrain.projet42.data.api.model.EventResponse
import com.technobrain.projet42.data.api.model.UserResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface Projet42API {

    @GET("/api/utilisateurs/infos")
    suspend fun userInfos(
        @Header("Authorization") token: String,
    ): Response<UserResponse>

    @GET("/evenements/api/byUser")
    suspend fun userEvents(
        @Header("Authorization") token: String,
    ): Response<List<EventResponse>>

}
