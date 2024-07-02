package com.technobrain.projet42.data.api.model

import com.google.gson.annotations.SerializedName

data class DocumentResponse(

    @SerializedName("id")
    val id: String,

    @SerializedName("fileName")
    val fileName: String,

)
