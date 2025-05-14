package com.evginozan.kargotakip.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.evginozan.kargotakip.domain.model.User
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

// Context extension olarak DataStore
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_preferences")

class UserPreferences(private val context: Context) {
    private val dataStore = context.dataStore

    private object PreferencesKeys {
        val TOKEN = stringPreferencesKey("auth_token")
        val USER_ID = longPreferencesKey("user_id")
        val USERNAME = stringPreferencesKey("username")
        val EMAIL = stringPreferencesKey("email")
        val FULL_NAME = stringPreferencesKey("full_name")
        val PHONE = stringPreferencesKey("phone")
    }

    suspend fun saveToken(token: String) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.TOKEN] = token
        }
        TokenManager.saveToken(token)
    }

    suspend fun getToken(): String? {
        val preferences = dataStore.data.first()
        val token = preferences[PreferencesKeys.TOKEN]
        token?.let { TokenManager.saveToken(it) }
        return token
    }

    suspend fun saveUser(user: User) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.USER_ID] = user.id
            preferences[PreferencesKeys.USERNAME] = user.username
            preferences[PreferencesKeys.EMAIL] = user.email
            preferences[PreferencesKeys.FULL_NAME] = user.fullName
            preferences[PreferencesKeys.PHONE] = user.phone
        }
    }

    suspend fun getUser(): User? {
        val preferences = dataStore.data.first()
        val id = preferences[PreferencesKeys.USER_ID] ?: return null
        val username = preferences[PreferencesKeys.USERNAME] ?: return null
        val email = preferences[PreferencesKeys.EMAIL] ?: return null
        val fullName = preferences[PreferencesKeys.FULL_NAME] ?: return null
        val phone = preferences[PreferencesKeys.PHONE] ?: return null
        val token = preferences[PreferencesKeys.TOKEN] ?: ""

        return User(id, username, email, fullName, phone, token)
    }

    suspend fun clearUserData() {
        dataStore.edit { preferences ->
            preferences.clear()
        }
        TokenManager.clearToken()
    }
}