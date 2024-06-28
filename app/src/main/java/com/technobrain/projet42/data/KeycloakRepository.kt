package com.technobrain.projet42.data

import android.content.Context
import com.technobrain.projet42.data.api.KeycloakAPI
import com.technobrain.projet42.data.api.SessionManager
import com.technobrain.projet42.data.api.model.KeycloakCredential
import com.technobrain.projet42.data.api.model.KeycloakUser
import com.technobrain.projet42.domain.repositories.LoginRepository
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.Objects


private const val BASE_URL = "http://192.168.1.30:8090/"
private const val CLIENT_ID = "projet42-api"
private const val CLIENT_SECRET = "eGWtjjG0tOWD5mqoaUp1onBgFNzNBIfT"
private const val USER_NAME_ADMIN = "admin"
private const val PASSWORD_ADMIN = "Password123!"

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

    override suspend fun register(username: String, password: String, email: String, firstName: String, lastName: String): Result<String> {
        return try {
            val response = api.login(
                clientId = CLIENT_ID,
                username = USER_NAME_ADMIN,
                password = PASSWORD_ADMIN,
                grantType = "password",
                clientSecret = CLIENT_SECRET
            )

            if (response.isSuccessful) {
                val tokenResponse = response.body()
                val accessToken = tokenResponse?.access_token ?: throw Exception("Token not found")

                try {
                    val credentials = listOf(
                        KeycloakCredential(
                            type = "password",
                            value = password,
                            temporary = false
                        )
                    )

                    val userRequest = KeycloakUser(
                        username = username,
                        email = email,
                        enabled = true,
                        credentials = credentials,
                        attributes = mapOf(
                            "photoProfil" to "",
                            "firstName" to firstName,
                            "lastName" to lastName
                        ),
                        emailVerified = true
                    )

                    val responseUser = api.register(
                        token = "Bearer "+accessToken,
                        user = userRequest
                    )
                    if (responseUser.isSuccessful) {
                        Result.success("success")
                    } else {
                        Result.failure(Exception("Register failed: ${responseUser.errorBody()?.string()}"))
                    }

                }catch (e: Exception) {
                    Result.failure(e)
                }

            } else {
                Result.failure(Exception("Register failed: ${response.errorBody()?.string()}"))
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