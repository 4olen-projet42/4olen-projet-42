package com.technobrain.projet42.data

import com.technobrain.projet42.domain.repositories.LoginRepository
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


private const val BASE_URL = "http://192.168.1.48:8089" //

class KeycloakRepository : LoginRepository {

    private val api: KeycloakAPI

    init {
        val logging = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }

        val okhttpClient = OkHttpClient.Builder()
            .addInterceptor(logging)  // ajout des logs sur les requêtes
            .build()

        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okhttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        api = retrofit.create(KeycloakAPI::class.java)
    }

    override suspend fun login(username: String, password: String): String {
        return api.login(LoginRequest("projet42", "jdoe", "Pass123", "password")).access_token
    }

}