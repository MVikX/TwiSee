package com.example.twisee.data.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_prefs")

class UserPreferences @Inject constructor(context: Context) {
    private val datastore = context.dataStore

    companion object {
        private val AUTH_TOKEN_KEY = stringPreferencesKey("auth_token")
    }

    suspend fun saveAuthToken(token: String) {
        datastore.edit { prefs -> prefs[AUTH_TOKEN_KEY] = token }
    }

    suspend fun getAuthToken(): String? {
        return datastore.data.map { prefs -> prefs[AUTH_TOKEN_KEY] }.first()
    }

    suspend fun clearAuthToken() {
        datastore.edit { prefs -> prefs.remove(AUTH_TOKEN_KEY) }
    }
}