package com.example.mynewplaylist.media.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mynewplaylist.media.domain.db.HistoryMediaInteractor
import com.example.mynewplaylist.search.domain.models.Track
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class NewPlaylistViewModel(private val historyMediaInteractor: HistoryMediaInteractor): ViewModel() {
    fun getFavoriteTracks(): Flow<ArrayList<Track>> = flow{
        historyMediaInteractor.historyTracks()
    }
}