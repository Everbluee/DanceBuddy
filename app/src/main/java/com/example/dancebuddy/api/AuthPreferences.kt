package com.example.dancebuddy.api

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.auth0.android.jwt.JWT
import com.example.dancebuddy.ContextProvider
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking

val Context.dataStore by preferencesDataStore(name = "auth")

object AuthPreferences {
    private val ACCESS_TOKEN_KEY = stringPreferencesKey("access_token")
    private val REFRESH_TOKEN_KEY = stringPreferencesKey("refresh_token")

    suspend fun saveAccessToken(token: String) {
        ContextProvider.appContext.dataStore.edit { preferences ->
            preferences[ACCESS_TOKEN_KEY] = token
        }
    }

    suspend fun saveRefreshToken(token: String) {
        ContextProvider.appContext.dataStore.edit { preferences ->
            preferences[REFRESH_TOKEN_KEY] = token
        }
    }

    fun getAccessToken(): Flow<String?> {
        return ContextProvider.appContext.dataStore.data.map { preferences ->
            preferences[ACCESS_TOKEN_KEY]
        }
    }

    fun getRefreshToken(): Flow<String?> {
        return ContextProvider.appContext.dataStore.data.map { preferences ->
            preferences[REFRESH_TOKEN_KEY]
        }
    }

    fun getUserIdFromToken(token: String): Int? {
        return try {
            val jwt = JWT(token)
            jwt.getClaim("user_id").asInt()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun decodeToken(): String? = runBlocking {
        getAccessToken().first()
    }

    suspend fun clearTokens() {
        ContextProvider.appContext.dataStore.edit { preferences ->
            preferences.remove(ACCESS_TOKEN_KEY)
            preferences.remove(REFRESH_TOKEN_KEY)
        }
    }
}
