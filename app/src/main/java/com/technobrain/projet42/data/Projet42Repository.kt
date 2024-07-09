package com.technobrain.projet42.data;


import android.content.Context;
import com.technobrain.projet42.R
import com.technobrain.projet42.data.api.KeycloakAPI
import com.technobrain.projet42.data.api.Projet42API
import com.technobrain.projet42.data.api.SessionManager;
import com.technobrain.projet42.data.api.model.DocumentResponse
import com.technobrain.projet42.data.api.model.UserResponse
import com.technobrain.projet42.domain.model.DocumentShort
import com.technobrain.projet42.data.api.model.EventResponse
import com.technobrain.projet42.data.api.model.StatsResponse
import com.technobrain.projet42.domain.model.EventShort
import com.technobrain.projet42.domain.model.StatShort
import com.technobrain.projet42.domain.model.UserShort
import com.technobrain.projet42.domain.repositories.ApiRepository
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.OkHttpClient;
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import java.io.File
import java.io.InputStream
import java.security.KeyStore
import java.security.cert.CertificateFactory
import java.security.cert.X509Certificate
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManagerFactory
import javax.net.ssl.X509TrustManager

private const val BASE_URL = "http://90.119.233.2:4433/" //A CHANGER EN FONCTION DE SON ENV

class Projet42Repository(context:Context): ApiRepository {

    private val api: Projet42API

    private val sessionManager: SessionManager = SessionManager(context)

    init {
        val retrofit = createRetrofit(context)
        api = retrofit.create(Projet42API::class.java)
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


    override suspend fun uploadFile(path: String, name: String): Result<String> {
        return try {
            val file = File(path)
            val requestBody = file.asRequestBody("application/*".toMediaTypeOrNull())
            val multipartBody = MultipartBody.Part.createFormData("file", name, requestBody)
            val token = "Bearer ${sessionManager.fetchAccessToken()}"

            val response = api.uploadFile(multipartBody, token)

            if (response.isSuccessful) {
                Result.success("success")
            } else {
                Result.failure(Exception("Failed to upload file: ${response.errorBody()?.string()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getDocuments(): Result<List<DocumentShort>> {
        return try {
            val response = api.getDocuments(
                token = "Bearer "+sessionManager.fetchAccessToken(),
            )
            if (response.isSuccessful) {
                val documentResponse = response.body()
                if (documentResponse != null) {
                    val listDocumentShort = documentResponse.map { it.toDocumentShort() }
                    Result.success(listDocumentShort)
                } else {
                    Result.failure(Exception("Document not found"))

                }
            } else {
                Result.failure(Exception("Document not found"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun deleteDocument(documentId: String): Result<String> {
        return try {
            val response = api.deleteDocument(
                token = "Bearer " + sessionManager.fetchAccessToken(),
                documentId = documentId
            )
            if (response.isSuccessful) {
                Result.success("success")
            } else {
                Result.failure(
                    Exception(
                        "Failed to delete document: ${
                            response.errorBody()?.string()
                        }"
                    )
                )
             }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }


    override suspend fun getUserEvents(): Result<List<EventShort>> {
        return try {
            val response = api.userEvents(
                token = "Bearer "+sessionManager.fetchAccessToken(),
            )

            if (response.isSuccessful) {
                val eventResponse = response.body()
                if (eventResponse != null) {
                    val eventShortList = eventResponse.map { it.toEventShort() }
                    Result.success(eventShortList)
                } else {
                    Result.failure(Exception("Events not found"))
                }
            } else {
                Result.failure(Exception("Events not found"))

            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getUserStats(): Result<StatShort> {
        return try {
            val response = api.userStats(
                token = "Bearer "+sessionManager.fetchAccessToken(),
            )

            if (response.isSuccessful) {
                val statsResponse = response.body()
                if (statsResponse != null) {
                    val statShort = statsResponse.toStatShort()
                    Result.success(statShort)
                } else {
                    Result.failure(Exception("Stats not found"))
                }
            } else {
                Result.failure(Exception("Stats not found"))
            }
            } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun searchEvent(search: String): Result<List<EventShort>> {
        return try {
            val response = api.searchEvent(search)

            if (response.isSuccessful) {
                val eventResponse = response.body()
                if (eventResponse != null) {
                    val eventShortList = eventResponse.map { it.toEventShort() }
                    Result.success(eventShortList)
                } else {
                    Result.failure(Exception("Event not found"))
                }
            } else {
                Result.failure(Exception("Event not found"))
            }

        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getEvents(): Result<List<EventShort>> {
        return try {
            val response = api.getEvents()

            if (response.isSuccessful) {
                val eventResponse = response.body()
                if (eventResponse != null) {
                    val eventShortList = eventResponse.map { it.toEventShort() }
                    Result.success(eventShortList)
                } else {
                    Result.failure(Exception("Event not found"))
                }
            } else {
                Result.failure(Exception("Event not found"))
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


private fun DocumentResponse.toDocumentShort() =
    DocumentShort(
        this.id,
        this.fileName
    )

private fun EventResponse.toEventShort() =
    EventShort(
        this.id.toString(),
        this.name,
        this.date,
        this.location,
        this.distance.toString()
    )

private fun StatsResponse.toStatShort() =
    StatShort(
        this.numberOfEvents.toString(),
        this.totalDistance.toString()
    )
