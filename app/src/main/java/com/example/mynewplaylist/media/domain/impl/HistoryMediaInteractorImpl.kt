package com.example.mynewplaylist.media.domain.impl

import com.example.mynewplaylist.media.data.db.entity.TrackEntity
import com.example.mynewplaylist.media.domain.db.HistoryMediaInteractor
import com.example.mynewplaylist.media.domain.db.HistoryMediaRepository
import com.example.mynewplaylist.search.data.dto.TrackDto
import com.example.mynewplaylist.search.domain.models.Track
import kotlinx.coroutines.flow.Flow

class HistoryMediaInteractorImpl(
    private val historyMediaRepository: HistoryMediaRepository
): HistoryMediaInteractor {
    override suspend fun insertTrack(track: Track) {
        historyMediaRepository.insertTrack(track)
    }

    override fun deleteTrack(track: Track) {
        historyMediaRepository.deleteTrack(track)
    }

    override fun historyTracks(): Flow<List<Track>> {
        return historyMediaRepository.historyTracks()
    }
}