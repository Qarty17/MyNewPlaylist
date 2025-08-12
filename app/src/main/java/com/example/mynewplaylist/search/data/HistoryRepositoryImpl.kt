package com.example.mynewplaylist.search.data

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.example.mynewplaylist.common.data.storage.PrefsStorageClient
import com.example.mynewplaylist.creator.Resource
import com.example.mynewplaylist.search.domain.api.HistoryRepository
import com.example.mynewplaylist.search.domain.models.Track
import com.example.mynewplaylist.search.ui.new_key
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class HistoryRepositoryImpl(context: Context): HistoryRepository {
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("search_history", Context.MODE_PRIVATE)
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
//        val tracks=storage.getData()?:arrayListOf()
//        tracks.add
        val json = gson.toJson(history)
        sharedPreferences.edit { putString(new_key, json) }
    }
    override fun clearHistory() {
        sharedPreferences.edit { remove(new_key) }
    }



}