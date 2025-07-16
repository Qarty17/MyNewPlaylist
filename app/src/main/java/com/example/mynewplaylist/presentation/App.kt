package com.example.mynewplaylist.presentation


import android.app.Application

import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.edit
import com.example.mynewplaylist.data.ThemeManager


const val EXAMPLE_PREFERENCES="new_pref"
const val KEY_PREFERENCES="new_key_pref"
class App: Application() {
    private var darkTheme=false

    override fun onCreate() {
        super.onCreate()
        val manager=ThemeManager(this)
        darkTheme=manager.getSavedTheme()
        if (darkTheme){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }else{
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }

    }
    fun switchTheme(darkThemeEnabled: Boolean){
        val manager=ThemeManager(this)
        darkTheme=darkThemeEnabled
        AppCompatDelegate.setDefaultNightMode(
            if(darkThemeEnabled){
                manager.saveTheme(darkTheme)
                AppCompatDelegate.MODE_NIGHT_YES
            }else{
                manager.saveTheme(darkTheme)
                AppCompatDelegate.MODE_NIGHT_NO
            }
        )
    }
    private fun getSavedTheme(): Boolean{
        val sharedPreferences=getSharedPreferences(EXAMPLE_PREFERENCES,MODE_PRIVATE)
        return sharedPreferences.getBoolean(KEY_PREFERENCES,false)
    }
    private fun saveTheme(isDarkTheme: Boolean){
        val sharedPreferences=getSharedPreferences(EXAMPLE_PREFERENCES,MODE_PRIVATE)
        sharedPreferences.edit {
            putBoolean(KEY_PREFERENCES, isDarkTheme)
        }
    }
}