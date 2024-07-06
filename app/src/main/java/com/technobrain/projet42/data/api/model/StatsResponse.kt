package com.technobrain.projet42.data.api.model;

import com.google.gson.annotations.SerializedName;
data class StatsResponse (

    @SerializedName("numberOfEvents")
    val numberOfEvents: Int,

    @SerializedName("totalDistance")
    val totalDistance: Double
)
