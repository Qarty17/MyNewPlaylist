package com.example.mynewplaylist.legacy.domain.api

import com.example.mynewplaylist.legacy.domain.models.Track


interface HistoryRepository {
    fun getHistory(): ArrayList<Track>
    fun saveHistory(history: ArrayList<Track>)
    fun clearHistory()
}