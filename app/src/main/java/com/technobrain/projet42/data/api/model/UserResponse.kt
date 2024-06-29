package com.technobrain.projet42.data.api.model

import com.google.gson.annotations.SerializedName

data class UserResponse(

    @SerializedName("email")
    val email: String,

    @SerializedName("firstName")
    val firstName: String,

    @SerializedName("lastName")
    val lastName: String,

    @SerializedName("photo_profil")
    val photoProfil: String

)