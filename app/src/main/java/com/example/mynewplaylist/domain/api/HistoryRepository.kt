package com.example.mynewplaylist.domain.api

import com.example.mynewplaylist.domain.models.Track


interface HistoryRepository {
    fun getHistory(): ArrayList<Track>
    fun saveHistory(history: ArrayList<Track>)
    fun clearHistory()
}