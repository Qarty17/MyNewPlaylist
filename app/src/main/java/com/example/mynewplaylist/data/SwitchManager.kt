package com.example.mynewplaylist.data

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity.MODE_PRIVATE

class SwitchManager(context: Context) {
    private val sharedPreferences: SharedPreferences =context.getSharedPreferences("switch1", MODE_PRIVATE)
    val editor=sharedPreferences.edit()
    fun getSavedSwitcher():Boolean{
        return sharedPreferences.getBoolean("switch1",false)
    }
    fun saveSwitcher(isTrue:Boolean){
        if(isTrue){
        editor.putBoolean("switch1",true)
        }
        else{
        editor.putBoolean("switch1",false)
        }
        editor.apply()
    }
}