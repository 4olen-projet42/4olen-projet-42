package com.technobrain.projet42.domain.model

data class EventDetail(
    val id: String,
    val nom: String,
    val image: String,
    val maxParticipants: Int,
    val date: String,
    val location: String,
    val distance: Int,
    val parcoursJSON: String,
    val denivele: Int,
    val heure: String,
)