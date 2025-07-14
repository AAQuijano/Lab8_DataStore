package com.quijano.lab_7_avatarbot

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

object DataStoreInstance {
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

    val BOOLEAN_KEY = booleanPreferencesKey("boolean_Key")
    val YEAR_KEY = intPreferencesKey("int_key")
    val NAME_KEY = stringPreferencesKey("name_key")
    val PHONE_KEY = stringPreferencesKey("phone_key")
    val HANDLE_KEY = stringPreferencesKey("handle_key")
    val ROLE_KEY = stringPreferencesKey("role_key")
    val EMAIL_KEY = stringPreferencesKey("email_key")

    suspend fun saveAllPreferences(
        context: Context,
        name: String,
        role: String,
        year: Int,
        phone: String,
        handle: String,
        email: String,
        showContactInfo: Boolean
    ) {
        context.dataStore.edit { preferences ->
            preferences[NAME_KEY] = name
            preferences[ROLE_KEY] = role
            preferences[YEAR_KEY] = year
            preferences[PHONE_KEY] = phone
            preferences[HANDLE_KEY] = handle
            preferences[EMAIL_KEY] = email
            preferences[BOOLEAN_KEY] = showContactInfo
        }
    }

    fun getBooleanPreferences(context: Context): Flow<Boolean> {
        return context.dataStore.data.map { preferences ->
            preferences[BOOLEAN_KEY] ?: true
        }
    }

    fun getStringPreferences(context: Context, key: Preferences.Key<String>): Flow<String> {
        return context.dataStore.data.map { preferences ->
            preferences[key] ?: when (key) {
                NAME_KEY -> "Antonio Quijano"
                ROLE_KEY -> "Estudiante de Ing. De Software"
                PHONE_KEY -> "+507: 6175-2856"
                HANDLE_KEY -> "@AAQuijano"
                EMAIL_KEY -> "alexisq05@hotmail.com"
                else -> ""
            }
        }
    }

    fun getIntPreferences(context: Context): Flow<Int> {
        return context.dataStore.data.map { preferences ->
            preferences[YEAR_KEY] ?: 4
        }
    }
}