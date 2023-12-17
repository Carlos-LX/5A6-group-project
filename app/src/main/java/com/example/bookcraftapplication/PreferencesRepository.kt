package com.example.bookcraftapplication

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
data class UserPrefs(
    val selectedTheme: Theme,
    val fontSize : Float,
)




class PreferencesRepository public constructor(private val dataStore: DataStore<Preferences>,
                                               private val context: Context
) {



    private object PreferenceKeys {
        val SELECTED_THEME = stringPreferencesKey("THEME")
        val FONT_SIZE = floatPreferencesKey("FONT_SIZE")
    }

 suspend fun setTheme(newTheme : Theme) {
     dataStore.edit { preference ->
         preference[PreferenceKeys.SELECTED_THEME] = newTheme.name
     }
 }
    suspend fun setFontSize(newSize : Float) {
        dataStore.edit { preference ->
            preference[PreferenceKeys.FONT_SIZE] = newSize
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
            val newSize = preferences[PreferenceKeys.FONT_SIZE] ?: 16f
            UserPrefs(Theme.valueOf(selectedTheme), newSize)
         }

 }
