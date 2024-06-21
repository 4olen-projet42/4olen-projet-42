package com.technobrain.projet42.data.api.model

import com.google.gson.annotations.SerializedName

data class KeycloakLoginResponse (

    @SerializedName("access_token")
    val accessToken: String,

    @SerializedName("expires_in")
    val expiresIn: Int,

    @SerializedName("refresh_expires_in")
    val refreshExpiresIn: Int,

    @SerializedName("refresh_token")
    val refreshToken: String,

    @SerializedName("token_type")
    val tokenType: String,

    @SerializedName("not_before_policy")
    val notBeforePolicy: Int,

    @SerializedName("session_state")
    val sessionState: String,

    @SerializedName("scope")
    val scope: String



)
