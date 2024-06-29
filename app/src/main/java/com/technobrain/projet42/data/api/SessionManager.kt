package com.technobrain.projet42.data.api

import android.content.Context
import android.content.SharedPreferences
import com.auth0.android.jwt.JWT
import java.util.Date

class SessionManager(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("session", Context.MODE_PRIVATE)
    private val editor = prefs.edit()


    companion object {
        private const val ACCESS_TOKEN = "access_token"
    }

    fun saveAccessToken(token: String) {
        editor.putString(ACCESS_TOKEN, token).apply()

    }

    fun fetchAccessToken(): String? {
        return prefs.getString(ACCESS_TOKEN, null)
    }

    fun clearAccessToken() {
        editor.remove(ACCESS_TOKEN).apply()
    }

    fun isTokenExpired(token: String): Boolean {
        val jwt = JWT(token)
        val expiresAt = jwt.expiresAt ?: return true
        return expiresAt.before(Date())
    }

    fun isUserLoggedIn(): Boolean {
        val token = fetchAccessToken()
        return token != null && !isTokenExpired(token)
    }

}