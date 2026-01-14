package com.example.mynewplaylist.settings.data.impl

import android.content.SharedPreferences
import com.example.mynewplaylist.settings.domain.api.SwitchRepository
import androidx.core.content.edit

class SwitchRepositoryImpl(private val sharedPreferences: SharedPreferences): SwitchRepository {
    override fun getSavedSwitcher():Boolean{
        return sharedPreferences.getBoolean("switch1",false)
    }
    override fun saveSwitcher(isTrue:Boolean){
        sharedPreferences.edit {
            putBoolean("switch1",isTrue)
        }
    }
}