package com.technobrain.projet42.data

data class LoginRequest(
    val client_id: String,
    val username: String,
    val password: String,
    val grant_type: String
)