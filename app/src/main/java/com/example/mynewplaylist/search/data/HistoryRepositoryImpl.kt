package com.example.mynewplaylist.search.data

import com.example.mynewplaylist.common.data.storage.PrefsStorageClient

import com.example.mynewplaylist.search.domain.api.HistoryRepository
import com.example.mynewplaylist.search.domain.models.Track
class HistoryRepositoryImpl(private val storage: PrefsStorageClient<ArrayList<Track>>): HistoryRepository {
//    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("search_history", Context.MODE_PRIVATE)
//    private val gson = Gson()
    override fun getHistory(): ArrayList<Track> {
        val tracks=storage.getData()?:arrayListOf()
        return tracks


//        val json = sharedPreferences.getString(new_key, null)
//        return if (json != null) {
//            val type = object : TypeToken<ArrayList<Track>>() {}.type
//            gson.fromJson(json, type) ?: ArrayList()
//        } else {
//            ArrayList()
//        }
    }

    override fun saveHistory(tracks: ArrayList<Track>) {
        storage.storageData(tracks)
//        val json = gson.toJson(history)
//        sharedPreferences.edit { putString(new_key, json) }
    }
    override fun clearHistory() {
        storage.removeData()
    }



}