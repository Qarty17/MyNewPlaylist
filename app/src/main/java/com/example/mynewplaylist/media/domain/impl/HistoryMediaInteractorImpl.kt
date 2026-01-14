package com.example.mynewplaylist.media.domain.impl

import com.example.mynewplaylist.media.data.db.entity.TrackEntity
import com.example.mynewplaylist.media.domain.db.HistoryMediaInteractor
import com.example.mynewplaylist.media.domain.db.HistoryMediaRepository
import com.example.mynewplaylist.media.domain.models.TrackData
import com.example.mynewplaylist.search.data.dto.TrackDto
import com.example.mynewplaylist.search.domain.models.Track
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class HistoryMediaInteractorImpl(
    private val historyMediaRepository: HistoryMediaRepository
): HistoryMediaInteractor {
    override suspend fun insertTrack(track: TrackData) {
        historyMediaRepository.insertTrack(track)
    }

    override suspend fun deleteTrack(track: TrackData) {
        historyMediaRepository.deleteTrack(track)
    }

    override suspend fun historyTracks(): Flow<List<TrackData>> {
        return historyMediaRepository.historyTracks()
    }

    override suspend fun getIdTrack(trackId: String): Flow<Long> {
        return historyMediaRepository.getIdTracks(trackId)
    }

    override suspend fun addToFavorites(trackId: String) {
        historyMediaRepository.addToFavorites(trackId)
    }

    override suspend fun removeFromFavorites(trackId: String) {
        historyMediaRepository.removeFromFavorites(trackId)
    }

    override suspend fun toggleFavorite(trackId: String) {
        historyMediaRepository.toggleFavorite(trackId)
    }

    override suspend fun getFavoriteTracks(): Flow<List<TrackData>> {
        return historyMediaRepository.getFavoriteTracks()
    }

    override suspend fun isTrackFavorite(trackId: String): Boolean {
        return historyMediaRepository.isTrackFavorite(trackId)
    }

    override suspend fun getTrackById(trackId: String): TrackData? {
        return historyMediaRepository.getTrackById(trackId)
    }
}