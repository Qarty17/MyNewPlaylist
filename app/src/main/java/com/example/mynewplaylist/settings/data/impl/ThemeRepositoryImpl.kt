package com.example.mynewplaylist.settings.data.impl

import android.content.Context
import android.content.SharedPreferences

import androidx.appcompat.app.AppCompatActivity.MODE_PRIVATE
import androidx.core.content.edit
import com.example.mynewplaylist.settings.domain.api.ThemeRepository

//const val EXAMPLE_PREFERENCES="new_pref"
const val KEY_PREFERENCES="new_key_pref"
class ThemeRepositoryImpl(private val sharedPreferences: SharedPreferences/*,context: Context*/):ThemeRepository {
    //private var sharedPreferences=context.getSharedPreferences(EXAMPLE_PREFERENCES, MODE_PRIVATE)
    override fun getSavedTheme(): Boolean{
        return sharedPreferences.getBoolean(KEY_PREFERENCES,false)
    }
    override fun saveTheme(isDarkTheme: Boolean){
        sharedPreferences.edit {
            putBoolean(KEY_PREFERENCES, isDarkTheme)
        }
    }

}