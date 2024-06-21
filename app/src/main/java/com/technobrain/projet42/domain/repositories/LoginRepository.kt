package com.technobrain.projet42.domain.repositories

interface LoginRepository {
    suspend fun login(username: String, password: String) : String

    suspend fun getAccessToken() : String?
}