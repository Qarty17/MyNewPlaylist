package com.example.mynewplaylist


import android.app.Application

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate



const val EXAMPLE_PREFERENCES="ex_pref"
const val KEY_PREFERENCES="key_pref"
class App: Application() {
    var darkTheme=false

    override fun onCreate() {
        super.onCreate()
        val sharedPr: SharedPreferences =getSharedPreferences(EXAMPLE_PREFERENCES,MODE_PRIVATE)
        val editor=sharedPr.edit()

        darkTheme=sharedPr.getBoolean(KEY_PREFERENCES,false)
        AppCompatDelegate.setDefaultNightMode(sharedPr.getInt("key_theme",1))
        if(darkTheme){
            editor.putBoolean(KEY_PREFERENCES,true)
            editor.putInt("key_theme",1)
            editor.apply()
        }else{
            editor.putBoolean(KEY_PREFERENCES,false)
            editor.putInt("key_theme",2)
            editor.apply()
        }



    }
    fun switchTheme(darkThemeEnabled: Boolean){
        darkTheme=darkThemeEnabled
        AppCompatDelegate.setDefaultNightMode(
            if(darkThemeEnabled){

                AppCompatDelegate.MODE_NIGHT_YES
            }else{

                AppCompatDelegate.MODE_NIGHT_NO
            }
        )
    }
}