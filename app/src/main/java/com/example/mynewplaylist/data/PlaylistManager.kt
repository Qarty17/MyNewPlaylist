package com.example.mynewplaylist.data

import android.content.Context
import android.content.SharedPreferences
import com.example.mynewplaylist.domain.models.Track
import com.example.mynewplaylist.ui.new_key
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import androidx.core.content.edit

class PlaylistManager(context: Context) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("search_history", Context.MODE_PRIVATE)
    private val gson = Gson()

    fun getHistory(): ArrayList<Track> {
        val json = sharedPreferences.getString(new_key, null)
        return if (json != null) {
            val type = object : TypeToken<ArrayList<Track>>() {}.type
            gson.fromJson(json, type) ?: ArrayList()
        } else {
            ArrayList()
        }
    }
    fun saveHistory(history: ArrayList<Track>) {
        val json = gson.toJson(history)
        sharedPreferences.edit { putString(new_key, json) }
    }
    fun clearHistory() {
        sharedPreferences.edit { remove(new_key) }
    }


}