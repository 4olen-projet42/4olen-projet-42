package com.technobrain.projet42.domain.model

data class EventItem(
    val id: String,
    val title: String,
    val posterUrl: String,
    val date : String,
    val description : String,
    val location : String,
    val nb_participants : Int
)