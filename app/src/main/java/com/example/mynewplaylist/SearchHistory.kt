package com.example.mynewplaylist

import android.content.ClipData.Item
import android.content.Context
import android.content.SharedPreferences
import android.widget.Toast
import com.google.gson.Gson
import androidx.core.content.edit
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.reflect.TypeToken

class SearchHistory(context: Context){
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("search_history", Context.MODE_PRIVATE)
    private val gson = Gson()
    private val maxHistorySize = 10

    fun getHistory(): ArrayList<Track> {
        val json = sharedPreferences.getString(new_key, null)
        return if (json != null) {
            val type = object : TypeToken<ArrayList<Track>>() {}.type
            gson.fromJson(json, type) ?: ArrayList()
        } else {
            ArrayList()
        }
    }

    fun onTrackClick(track: Track) {
        val history = getHistory()
        history.removeAll { it.trackId == track.trackId }
        history.add(0, track)
        if (history.size > maxHistorySize) {
            history.subList(maxHistorySize, history.size).clear()
        }
        saveHistory(history)
    }

    fun clearHistory() {
        sharedPreferences.edit().remove(new_key).apply()
    }

    private fun saveHistory(history: ArrayList<Track>) {
        val json = gson.toJson(history)
        sharedPreferences.edit().putString(new_key, json).apply()
    }
}
//    var historyTracks: ArrayList<Track> = ArrayList<Track>()
//    //private lateinit var listener: SharedPreferences.OnSharedPreferenceChangeListener
//    val sharedPreferences: SharedPreferences=context.getSharedPreferences("name", Context.MODE_PRIVATE)
//    private val gson = Gson()
//    fun getHistory(): ArrayList<Track> {
//        val json = sharedPreferences.getString(new_key, null)
//        return if (json != null) {
//            val type = object : TypeToken<ArrayList<Track>>() {}.type
//            gson.fromJson(json, type) ?: ArrayList<Track>()
//        } else {
//            ArrayList<Track>()
//        }
//    }
//    fun clearHistory() {
//        sharedPreferences.edit { remove(new_key) }
//    }
//
//    fun onTrackClick(track: Track) {
//        val history=getHistory()
//        history.removeAll { it.trackId == track.trackId }
//        history.add(0, track)
//        if (history.size > 9)
//        {        history.subList(9, history.size).clear()    }
//        saveHistory(history)
//    }
//    private fun saveHistory(history: ArrayList<Track>){
//        val json=gson.toJson(history)
//        sharedPreferences.edit{ putString(new_key, json) }
//    }

