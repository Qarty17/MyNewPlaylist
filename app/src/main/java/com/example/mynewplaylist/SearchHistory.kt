package com.example.mynewplaylist

import android.content.Context
import android.content.SharedPreferences
import android.widget.Toast
import com.google.gson.Gson
import androidx.core.content.edit
import androidx.recyclerview.widget.RecyclerView

class SearchHistory(context: Context){
    var historyTracks= ArrayList<Track>()
    private lateinit var listener: SharedPreferences.OnSharedPreferenceChangeListener
    //lateinit var listener: SharedPreferences.OnSharedPreferenceChangeListener
    //private val sharedPreferences: SharedPreferences=context.getSharedPreferences("name", Context.MODE_PRIVATE)
    fun getHistory(): ArrayList<Track> {





        listener= SharedPreferences.OnSharedPreferenceChangeListener{sharedPreferences,key->
            if (key==new_key){
                val json = sharedPreferences.getString(new_key, null)
                if (json!=null){
                    val track=createTrackFromJson(json)
                    historyTracks.add(track)
                }
            }
        }
        //sharedPreferences.registerOnSharedPreferenceChangeListener(listener)

        return historyTracks
    }
//    fun getHistory(): ArrayList<Track> {
//        listener= SharedPreferences.OnSharedPreferenceChangeListener{sharedPreferences,key->
//            if(key==new_key){
//                val track=sharedPreferences?.getString(new_key,null)
//                if (track!=null){
//                    historyAdapter.tracks.add(0,createTrackFromJson(track))
//                    historyAdapter.notifyItemInserted(0)
//                }
//
//            }
//        }
//        sharedPreferences.registerOnSharedPreferenceChangeListener (listener)
//        historyAdapter.tracks=historyTracks
//        return historyTracks
//    }
    fun onTrackClick(track: Track) {

        historyTracks.removeAll { it.trackId == track.trackId }
        historyTracks.add(0, track)
        if (historyTracks.size > 9)
        {        historyTracks.subList(9, historyTracks.size).clear()    }
        //sharedPreferences.edit{ putString(new_key, createJsonFromTrack(track)) }
    }
    private fun createJsonFromTrack(track: Track): String {
        return Gson().toJson(track)
    }

    private fun createTrackFromJson(json: String?): Track {
        return Gson().fromJson(json, Track::class.java)
    }
}