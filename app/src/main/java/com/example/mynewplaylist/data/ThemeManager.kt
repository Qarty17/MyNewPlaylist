package com.example.mynewplaylist.data

import android.content.Context

import androidx.appcompat.app.AppCompatActivity.MODE_PRIVATE
import androidx.core.content.edit
const val EXAMPLE_PREFERENCES="new_pref"
const val KEY_PREFERENCES="new_key_pref"
class ThemeManager(context: Context) {
    private var sharedPreferences=context.getSharedPreferences(EXAMPLE_PREFERENCES, MODE_PRIVATE)
    fun getSavedTheme(): Boolean{
        return sharedPreferences.getBoolean(KEY_PREFERENCES,false)
    }
    fun saveTheme(isDarkTheme: Boolean){
        sharedPreferences.edit {
            putBoolean(KEY_PREFERENCES, isDarkTheme)
        }
    }

}