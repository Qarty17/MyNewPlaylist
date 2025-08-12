package com.example.mynewplaylist.search.domain.api

import com.example.mynewplaylist.search.domain.models.Track

interface HistoryInteractor {
    fun getHistory(): ArrayList<Track>
    fun saveHistory(history: ArrayList<Track>)
    fun clearHistory()
}