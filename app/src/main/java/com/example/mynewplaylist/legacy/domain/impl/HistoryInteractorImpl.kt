package com.example.mynewplaylist.legacy.domain.impl


import com.example.mynewplaylist.legacy.domain.api.HistoryInteractor
import com.example.mynewplaylist.legacy.domain.api.HistoryRepository
import com.example.mynewplaylist.legacy.domain.models.Track

class HistoryInteractorImpl(private val historyRepository: HistoryRepository):HistoryInteractor {

    override fun getHistory(): ArrayList<Track> {
        return historyRepository.getHistory()
    }

    override fun saveHistory(history: ArrayList<Track>) {
        historyRepository.saveHistory(history)
    }

    override fun clearHistory() {
        historyRepository.clearHistory()
    }
}