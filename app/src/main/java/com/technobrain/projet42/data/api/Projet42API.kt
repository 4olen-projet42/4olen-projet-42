package com.technobrain.projet42.data.api;

import com.technobrain.projet42.data.api.model.DocumentResponse
import com.technobrain.projet42.data.api.model.UserResponse
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface Projet42API {

    @GET("/api/utilisateurs/infos")
    suspend fun userInfos(
        @Header("Authorization") token: String,
    ): Response<UserResponse>

    @Multipart
    @POST("/api/documents/upload")
    suspend fun uploadFile(
        @Part file: MultipartBody.Part,
        @Header("Authorization") token: String
    ): Response<ResponseBody>

    @GET("/api/documents/list")
    suspend fun getDocuments(
        @Header("Authorization") token: String,
    ): Response<List<DocumentResponse>>

}
