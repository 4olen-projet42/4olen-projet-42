package com.technobrain.projet42.data

import android.content.Context
import com.technobrain.projet42.R
import com.technobrain.projet42.data.api.KeycloakAPI
import com.technobrain.projet42.data.api.SessionManager
import com.technobrain.projet42.data.api.model.KeycloakCredential
import com.technobrain.projet42.data.api.model.KeycloakUser
import com.technobrain.projet42.domain.repositories.LoginRepository
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.InputStream
import java.security.KeyStore
import java.security.cert.CertificateFactory
import java.security.cert.X509Certificate
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManagerFactory
import javax.net.ssl.X509TrustManager


private const val BASE_URL = "https://keycloak.doubli.fr:8443/" //A CHANGER EN FONCTION DE SON ENV
private const val CLIENT_ID = "projet42-api"
private const val CLIENT_SECRET = "b2CsfxhZVVzLqpy8GRigLI5TgWI4YzU0" //A CHANGER EN FONCTION DE SON ENV
private const val USER_NAME_ADMIN = "admin"
private const val PASSWORD_ADMIN = "Password123!"


class KeycloakRepository(context: Context) : LoginRepository {

    private val api: KeycloakAPI

    private val sessionManager: SessionManager = SessionManager(context)

    init {
        val retrofit = createRetrofit(context)
        api = retrofit.create(KeycloakAPI::class.java)
    }

    fun getCustomOkHttpClient(context: Context): OkHttpClient {
        try {
            // Charger le certificat CA depuis le fichier raw
            val certificateFactory = CertificateFactory.getInstance("X.509")
            val caInputStream: InputStream = context.resources.openRawResource(R.raw.server)
            val ca: X509Certificate = certificateFactory.generateCertificate(caInputStream) as X509Certificate
            caInputStream.close()

            // Créer un keystore contenant le certificat CA
            val keyStoreType = KeyStore.getDefaultType()
            val keyStore = KeyStore.getInstance(keyStoreType).apply {
                load(null, null)
                setCertificateEntry("ca", ca)
            }

            // Créer un TrustManager qui utilise le keystore CA
            val trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm())
            trustManagerFactory.init(keyStore)
            val trustManagers = trustManagerFactory.trustManagers

            // Créer un SSLContext utilisant les TrustManagers CA
            val sslContext = SSLContext.getInstance("TLS")
            sslContext.init(null, trustManagers, java.security.SecureRandom())

            val sslSocketFactory = sslContext.socketFactory

            // Créer un OkHttpClient qui utilise ce SSLContext
            val logging = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }

            // Ajout du HostnameVerifier pour accepter tous les noms d'hôtes
            val hostnameVerifier = HostnameVerifier { _, _ -> true }

            return OkHttpClient.Builder()
                .sslSocketFactory(sslSocketFactory, trustManagerFactory.trustManagers[0] as X509TrustManager)
                .hostnameVerifier(hostnameVerifier)
                .addInterceptor(logging)
                .build()

        } catch (e: Exception) {
            e.printStackTrace()
            throw RuntimeException(e)
        }
    }

    fun createRetrofit(context: Context): Retrofit {
        val okhttpClient = getCustomOkHttpClient(context)

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okhttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
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