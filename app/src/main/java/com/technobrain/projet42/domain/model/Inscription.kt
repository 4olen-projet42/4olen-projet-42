package com.technobrain.projet42.domain.model


data class Inscription(
    val id: Long,
    val valide: Int,
    val evenement: EventDetail
)