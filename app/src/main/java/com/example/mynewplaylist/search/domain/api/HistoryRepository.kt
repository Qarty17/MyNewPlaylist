package com.example.mynewplaylist.search.domain.api

import com.example.mynewplaylist.creator.Resource
import com.example.mynewplaylist.search.domain.models.Track

interface HistoryRepository {
    fun getHistory(): ArrayList<Track>
    fun saveHistory(history:ArrayList<Track>)
    fun clearHistory()
}