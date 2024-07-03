package com.technobrain.projet42.data.api

import com.technobrain.projet42.data.TokenResponse
import com.technobrain.projet42.data.api.model.KeycloakUser
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface KeycloakAPI {

    @FormUrlEncoded
    @POST("/realms/projet42/protocol/openid-connect/token")
    suspend fun login(
        @Field("client_id") clientId: String,
        @Field("username") username: String,
        @Field("password") password: String,
        @Field("grant_type") grantType: String,
        @Field("client_secret") clientSecret: String
    ): Response<TokenResponse>

    @POST("/admin/realms/projet42/users")
    suspend fun register(
        @Header("Authorization") token: String,
        @Body user: KeycloakUser
    ): Response<TokenResponse>

}