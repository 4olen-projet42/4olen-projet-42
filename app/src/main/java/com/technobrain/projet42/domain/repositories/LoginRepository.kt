package com.technobrain.projet42.domain.repositories

interface LoginRepository {
    suspend fun login(username: String, password: String) : Result<String>

    suspend fun getAccessToken() : String?

    suspend fun isUserLoggedIn() : Boolean

    suspend fun logout()

}