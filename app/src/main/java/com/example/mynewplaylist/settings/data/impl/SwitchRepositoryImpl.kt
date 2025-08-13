package com.example.mynewplaylist.settings.data.impl

import android.content.Context
import android.content.SharedPreferences
import com.example.mynewplaylist.settings.domain.api.SwitchRepository

class SwitchRepositoryImpl(context: Context): SwitchRepository {
    private val sharedPreferences: SharedPreferences =context.getSharedPreferences("switch1",
        Context.MODE_PRIVATE
    )
    private val editor=sharedPreferences.edit()
    override fun getSavedSwitcher():Boolean{
        return sharedPreferences.getBoolean("switch1",false)
    }
    override fun saveSwitcher(isTrue:Boolean){
        if(isTrue){
        editor.putBoolean("switch1",true)
        }
        else{
        editor.putBoolean("switch1",false)
        }
        editor.apply()
    }
}