package com.example.mynewplaylist.settings.data.impl


import android.content.SharedPreferences


import androidx.core.content.edit
import com.example.mynewplaylist.settings.domain.api.ThemeRepository


const val KEY_PREFERENCES="new_key_pref"
class ThemeRepositoryImpl(private val sharedPreferences: SharedPreferences):ThemeRepository {

    override fun getSavedTheme(): Boolean{
        return sharedPreferences.getBoolean(KEY_PREFERENCES,false)
    }
    override fun saveTheme(isDarkTheme: Boolean){
        sharedPreferences.edit {
            putBoolean(KEY_PREFERENCES, isDarkTheme)
        }
    }

}