package com.technobrain.projet42.data

data class TokenResponse(
    val access_token: String,
    val refresh_token: String,
    val expires_in: Int,
    val refresh_expires_in: Int,
    val token_type: String,
    val not_before_policy: Int,
    val session_state: String,
    val scope: String
)