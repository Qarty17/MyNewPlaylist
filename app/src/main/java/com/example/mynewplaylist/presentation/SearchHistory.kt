package com.example.mynewplaylist.presentation

import android.content.Context
import com.example.mynewplaylist.creator.Creator
import com.example.mynewplaylist.legacy.domain.models.Track

class SearchHistory(context: Context){
    val manager=Creator.provideHistoryInteractor(context)
    private val maxHistorySize = 10
    fun onTrackClick(track: Track) {
        val history = manager.getHistory()
        history.removeAll { it.trackId == track.trackId }
        history.add(0, track)
        if (history.size > maxHistorySize) {
            history.subList(maxHistorySize, history.size).clear()
        }
        manager.saveHistory(history)
    }

}



