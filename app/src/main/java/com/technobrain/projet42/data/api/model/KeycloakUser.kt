package com.technobrain.projet42.data.api.model

data class KeycloakUser(
    val username: String,
    val email: String,
    val enabled: Boolean,
    val credentials: List<KeycloakCredential>,
    val attributes: Map<String, String>,
    val emailVerified: Boolean
)

