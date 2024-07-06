package com.technobrain.projet42.data;


import android.content.Context;
import com.technobrain.projet42.data.api.Projet42API
import com.technobrain.projet42.data.api.SessionManager;
import com.technobrain.projet42.data.api.model.DocumentResponse
import com.technobrain.projet42.data.api.model.UserResponse
import com.technobrain.projet42.domain.model.DocumentShort
import com.technobrain.projet42.data.api.model.EventResponse
import com.technobrain.projet42.domain.model.EventShort
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

private const val BASE_URL = "http://192.168.1.30:8080/" //A CHANGER EN FONCTION DE SON ENV

class Projet42Repository(context:Context): ApiRepository {

    private val api: Projet42API

    private val sessionManager: SessionManager = SessionManager(context)

    init {
        val logging = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }

        val okhttpClient = OkHttpClient.Builder()
                .addInterceptor(logging)
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
