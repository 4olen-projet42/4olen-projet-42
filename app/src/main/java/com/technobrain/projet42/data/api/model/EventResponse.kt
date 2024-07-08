package com.technobrain.projet42.data.api.model

import com.google.gson.annotations.SerializedName

data class EventResponse (
    @SerializedName("id")
    val id: Int,
    @SerializedName("nom")
    val name: String,
    @SerializedName("image")
    val image: String,
    @SerializedName("maxParticipants")
    val maxParticipants: Int,
    @SerializedName("dateDebut")
    val date: String,
    @SerializedName("ville")
    val location: String,
    @SerializedName("distance")
    val distance: Int,
    @SerializedName("parcoursJSON")
    val parcoursJSON: String,
    @SerializedName("denivele")
    val denivele: Int,
    @SerializedName("heureDebut")
    val heure: String
)
