package com.example.mynewplaylist.media.domain.db

import com.example.mynewplaylist.media.data.db.entity.TrackEntity
import com.example.mynewplaylist.search.data.dto.TrackDto
import com.example.mynewplaylist.search.domain.models.Track
import kotlinx.coroutines.flow.Flow

interface HistoryMediaInteractor {
    suspend fun insertTrack(track: Track)
    suspend fun deleteTrack(track: Track)
    suspend fun historyTracks(): Flow<List<Track>>
    suspend fun getIdTrack(trackId: Long):Flow<Long>
}