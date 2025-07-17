package com.example.mynewplaylist.data

import android.content.Context

import androidx.appcompat.app.AppCompatActivity.MODE_PRIVATE
import androidx.core.content.edit
import com.example.mynewplaylist.domain.api.ThemeRepository

const val EXAMPLE_PREFERENCES="new_pref"
const val KEY_PREFERENCES="new_key_pref"
class ThemeRepositoryImpl(context: Context):ThemeRepository {
    private var sharedPreferences=context.getSharedPreferences(EXAMPLE_PREFERENCES, MODE_PRIVATE)
    override fun getSavedTheme(): Boolean{
        return sharedPreferences.getBoolean(KEY_PREFERENCES,false)
    }
    override fun saveTheme(isDarkTheme: Boolean){
        sharedPreferences.edit {
            putBoolean(KEY_PREFERENCES, isDarkTheme)
        }
    }

}