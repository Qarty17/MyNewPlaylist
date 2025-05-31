package com.example.mynewplaylist


import android.app.Application

import android.content.SharedPreferences
import android.text.BoringLayout
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.edit


const val EXAMPLE_PREFERENCES="new_pref"
const val KEY_PREFERENCES="new_key_pref"
class App: Application() {
    var darkTheme=false

    override fun onCreate() {
        super.onCreate()

        darkTheme=getSavedTheme()
        if (darkTheme){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }else{
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
        //val sharedPr: SharedPreferences =getSharedPreferences(EXAMPLE_PREFERENCES,MODE_PRIVATE)
        //val sharedPr2: SharedPreferences =getSharedPreferences("IntPreference",MODE_PRIVATE)
//        val editor2=sharedPr2.edit()
//        val editor=sharedPr.edit()
//
//
//        darkTheme=sharedPr.getBoolean(KEY_PREFERENCES,false)
//        var dayNight=sharedPr2.getInt("intPref",2)
//        AppCompatDelegate.setDefaultNightMode(dayNight)
//
//        if(darkTheme){
//            editor.putBoolean(KEY_PREFERENCES,true)
//            editor.apply()
//            editor2.putInt("intPref",1)
//            editor2.apply()
//        }else{
//            editor.putBoolean(KEY_PREFERENCES,false)
//            editor.apply()
//            editor2.putInt("intPref",2)
//            editor2.apply()
//        }



    }
    fun switchTheme(darkThemeEnabled: Boolean){
        darkTheme=darkThemeEnabled
        AppCompatDelegate.setDefaultNightMode(
            if(darkThemeEnabled){
                saveTheme(darkTheme)
                AppCompatDelegate.MODE_NIGHT_YES
            }else{
                saveTheme(darkTheme)
                AppCompatDelegate.MODE_NIGHT_NO
            }
        )
    }
    fun getSavedTheme(): Boolean{
        val sharedPreferences=getSharedPreferences(EXAMPLE_PREFERENCES,MODE_PRIVATE)
        return sharedPreferences.getBoolean(KEY_PREFERENCES,false)
    }
    fun saveTheme(isDarkTheme: Boolean){
        val sharedPreferences=getSharedPreferences(EXAMPLE_PREFERENCES,MODE_PRIVATE)
        sharedPreferences.edit {
            putBoolean(KEY_PREFERENCES, isDarkTheme)
        }
    }
}