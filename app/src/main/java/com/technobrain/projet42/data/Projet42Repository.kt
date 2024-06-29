package com.technobrain.projet42.data;


import android.content.Context;

import com.technobrain.projet42.data.api.Projet42API
import com.technobrain.projet42.data.api.SessionManager;
import com.technobrain.projet42.data.api.model.UserResponse
import com.technobrain.projet42.domain.model.UserShort
import com.technobrain.projet42.domain.repositories.ApiRepository

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

private const val BASE_URL = "http://172.20.10.10:8080/" //A CHANGER EN FONCTION DE SON ENV

class Projet42Repository(context:Context): ApiRepository {

    private val api: Projet42API

    private val sessionManager: SessionManager = SessionManager(context)

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
        api = retrofit.create(Projet42API::class.java)
    }

    override suspend fun getUserInfos(): Result<UserShort> {
        return try {
            val response = api.userInfos(
                token = "Bearer "+sessionManager.fetchAccessToken(),
            )

            if (response.isSuccessful) {
                val userResponse = response.body()
                if (userResponse != null) {
                    val userShort = userResponse.toUserShort()
                    Result.success(userShort)
                } else {
                    Result.failure(Exception("User not found"))
                }
            } else {
                Result.failure(Exception("User not found"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

private fun UserResponse.toUserShort() =
    UserShort(
        this.email,
        this.firstName,
        this.lastName,
        this.photoProfil
    )
