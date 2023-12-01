package com.example.spotifyclone

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
data class UserPrefs(val selectedTheme: Theme)




class PreferencesRepository public constructor(private val dataStore: DataStore<Preferences>,
                                               private val context: Context
) {



    private object PreferenceKeys {
        val SELECTED_THEME = stringPreferencesKey("THEME")
    }

 suspend fun setTheme(newTheme : Theme) {
     dataStore.edit { preference ->
         preference[PreferenceKeys.SELECTED_THEME] = newTheme.name
     }
 }


    val userPreferencesFlow: Flow<UserPrefs> = dataStore.data
         .catch { exception ->
             if (exception is IOException) {
                 emit(emptyPreferences())
             } else {
                 throw exception
             }
         }.map { preferences ->
             val selectedTheme = preferences[PreferenceKeys.SELECTED_THEME] ?: Theme.Light.name
             UserPrefs(Theme.valueOf(selectedTheme))
         }

 }
