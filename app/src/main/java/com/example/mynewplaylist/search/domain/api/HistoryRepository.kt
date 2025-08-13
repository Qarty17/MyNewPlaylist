package com.example.mynewplaylist.search.domain.api


import com.example.mynewplaylist.search.domain.models.Track

interface HistoryRepository {
    fun getHistory(): ArrayList<Track>
    fun saveHistory(tracks: ArrayList<Track>)
    fun clearHistory()
}