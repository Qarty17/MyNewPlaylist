package com.example.mynewplaylist.media.domain.db

import com.example.mynewplaylist.media.data.db.entity.TrackEntity
import com.example.mynewplaylist.media.domain.models.TrackData
import com.example.mynewplaylist.search.data.dto.TrackDto
import com.example.mynewplaylist.search.domain.models.Track
import kotlinx.coroutines.flow.Flow

interface HistoryMediaInteractor {
    suspend fun insertTrack(track: TrackData)
    suspend fun deleteTrack(track: TrackData)
    suspend fun historyTracks(): Flow<List<TrackData>>
    suspend fun getIdTrack(trackId: String): Flow<Long> // Изменил на String
    suspend fun addToFavorites(trackId: String)
    suspend fun removeFromFavorites(trackId: String)
    suspend fun toggleFavorite(trackId: String)
    suspend fun getFavoriteTracks(): Flow<List<TrackData>>
    suspend fun isTrackFavorite(trackId: String): Boolean
    suspend fun getTrackById(trackId: String): TrackData?
}