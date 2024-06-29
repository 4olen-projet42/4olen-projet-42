package com.technobrain.projet42.data

import android.content.Context
import com.technobrain.projet42.data.api.KeycloakAPI
import com.technobrain.projet42.data.api.SessionManager
import com.technobrain.projet42.domain.repositories.LoginRepository
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.Objects



private const val BASE_URL = "http://172.20.10.10:8090/" //A CHANGER EN FONCTION DE SON ENV
private const val CLIENT_ID = "projet42-api"
private const val CLIENT_SECRET = "R4vN42or8rBlsVbS4TMRsaUtOZQTvbSG" //A CHANGER EN FONCTION DE SON ENV

class KeycloakRepository(context: Context) : LoginRepository {

    private val api: KeycloakAPI

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
        api = retrofit.create(KeycloakAPI::class.java)
    }

    override suspend fun login(username: String, password: String): Result<String> {
        return try {
            val response = api.login(
                clientId = CLIENT_ID,
                username = username,
                password = password,
                grantType = "password",
                clientSecret = CLIENT_SECRET
            )

            if (response.isSuccessful) {
                val tokenResponse = response.body()
                val accessToken = tokenResponse?.access_token ?: throw Exception("Token not found")
                sessionManager.saveAccessToken(accessToken)
                Result.success(accessToken)
            } else {
                Result.failure(Exception("Login failed: ${response.errorBody()?.string()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)

        }
    }

    override suspend fun getAccessToken(): String? {
        return sessionManager.fetchAccessToken()
    }

    override suspend fun isUserLoggedIn(): Boolean {
        return sessionManager.isUserLoggedIn()
    }

    override suspend fun logout() {
        sessionManager.clearAccessToken()
    }

}