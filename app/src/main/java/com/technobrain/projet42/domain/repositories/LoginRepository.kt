package com.technobrain.projet42.domain.repositories

interface LoginRepository {
    suspend fun login(username: String, password: String) : Result<String>

    suspend fun register(username: String, password: String, email: String, firstName: String, lastName: String) : Result<String>

    suspend fun getAccessToken() : String?

    suspend fun isUserLoggedIn() : Boolean

    suspend fun logout()
}