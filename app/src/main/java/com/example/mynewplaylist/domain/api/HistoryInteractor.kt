package com.example.mynewplaylist.domain.api

import com.example.mynewplaylist.domain.models.Track

interface HistoryInteractor {
    fun getHistory(): ArrayList<Track>
    fun saveHistory(history: ArrayList<Track>)
    fun clearHistory()
}