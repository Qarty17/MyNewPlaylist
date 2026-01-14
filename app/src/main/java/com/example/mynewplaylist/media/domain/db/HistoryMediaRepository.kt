package com.example.mynewplaylist.media.domain.db

import com.example.mynewplaylist.media.domain.models.TrackData
import kotlinx.coroutines.flow.Flow

interface HistoryMediaRepository {
    suspend fun insertTrack(track: TrackData)
    suspend fun deleteTrack(track: TrackData)
    suspend fun historyTracks(): Flow<List<TrackData>>
    suspend fun getIdTracks(trackId: String): Flow<Long>
    suspend fun addToFavorites(trackId: String)
    suspend fun removeFromFavorites(trackId: String)
    suspend fun toggleFavorite(trackId: String)
    suspend fun getFavoriteTracks(): Flow<List<TrackData>>
    suspend fun isTrackFavorite(trackId: String): Boolean// добавил
    suspend fun getTrackById(trackId: String): TrackData?
}