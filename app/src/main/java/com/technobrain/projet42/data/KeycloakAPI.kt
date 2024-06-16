package com.technobrain.projet42.data

import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface KeycloakAPI {

    @Headers(
        "Content-Type: application/x-www-form-urlencoded",
        "Authorization: Basic 4t292JaimTYiRaJ61CNR42fw0pOxUHyn" // le client secret dans keycloak
    )
    @POST("/realms/spring-td/protocol/openid-connect/token/")
    suspend fun login(
        @Body loginRequest: LoginRequest
    ): LoginResponse
}