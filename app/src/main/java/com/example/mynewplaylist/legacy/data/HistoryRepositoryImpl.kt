package com.example.mynewplaylist.legacy.data

import android.content.Context
import android.content.SharedPreferences
import com.example.mynewplaylist.legacy.domain.models.Track
import com.example.mynewplaylist.ui.new_key
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import androidx.core.content.edit
import com.example.mynewplaylist.legacy.domain.api.HistoryRepository

class HistoryRepositoryImpl(context: Context):HistoryRepository {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("search_history", Context.MODE_PRIVATE)
    private val gson = Gson()

    override fun getHistory(): ArrayList<Track> {
        val json = sharedPreferences.getString(new_key, null)
        return if (json != null) {
            val type = object : TypeToken<ArrayList<Track>>() {}.type
            gson.fromJson(json, type) ?: ArrayList()
        } else {
            ArrayList()
        }
    }
    override fun saveHistory(history: ArrayList<Track>) {
        val json = gson.toJson(history)
        sharedPreferences.edit { putString(new_key, json) }
    }
    override fun clearHistory() {
        sharedPreferences.edit { remove(new_key) }
    }


}