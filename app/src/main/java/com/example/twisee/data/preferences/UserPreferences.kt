package com.example.twisee.data.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

// DataStore
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_prefs")

class UserPreferences @Inject constructor(@ApplicationContext context: Context) {
    private val datastore = context.dataStore

    companion object {
        private val AUTH_TOKEN_KEY = stringPreferencesKey("auth_token") // ключ для хранения токена
    }

    // токен в DataStore
    suspend fun saveAuthToken(token: String) {
        datastore.edit { prefs ->
            prefs[AUTH_TOKEN_KEY] = token
        }
    }

    // получение сохраненного токена
    suspend fun getAuthToken(): String? {
        return datastore.data.map { prefs ->
            prefs[AUTH_TOKEN_KEY]
        }.first()
    }

    // очистка токена
    suspend fun clearAuthToken() {
        datastore.edit { prefs ->
            prefs.remove(AUTH_TOKEN_KEY)
        }
    }
}
