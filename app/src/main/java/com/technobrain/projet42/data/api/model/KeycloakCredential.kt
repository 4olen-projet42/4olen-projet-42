package com.technobrain.projet42.data.api.model


data class KeycloakCredential(
    val type: String,
    val value: String,
    val temporary: Boolean
)