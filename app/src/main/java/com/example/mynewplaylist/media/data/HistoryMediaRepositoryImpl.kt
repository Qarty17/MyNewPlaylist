package com.example.mynewplaylist.media.data

import android.R.attr.track
import com.example.mynewplaylist.media.data.converters.TrackDbConvertor
import com.example.mynewplaylist.media.data.db.AppDataBase
import com.example.mynewplaylist.media.data.db.entity.TrackEntity
import com.example.mynewplaylist.media.domain.db.HistoryMediaRepository
import com.example.mynewplaylist.media.domain.models.TrackData
import com.example.mynewplaylist.search.domain.models.Track
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class HistoryMediaRepositoryImpl(
    private val appDataBase: AppDataBase,
    private val trackDbConvertor: TrackDbConvertor
): HistoryMediaRepository {
    override suspend fun insertTrack(track: TrackData)   {

        val track=convertFromTrackEntity(track)
        appDataBase.trackDao().insertTracks(track)
    }

    override suspend fun deleteTrack(track: TrackData) {
        val track=convertFromTrackEntity(track)
        appDataBase.trackDao().deleteTracks(track)
    }

    override suspend fun historyTracks(): Flow<List<TrackData>> = flow{
        val tracks=appDataBase.trackDao().getTracks()
        emit(convertFromTracksEntity(tracks))
    }

    override suspend fun getIdTracks(trackId: String): Flow<Long> =flow {
        val trackEntity = appDataBase.trackDao().getIdTracks(trackId)
        if (trackEntity != null) {

            emit(trackEntity.trackId.toLongOrNull() ?: 0L)
        } else {
            emit(0L)
        }
    }

    override suspend fun addToFavorites(trackId: String) {
        appDataBase.trackDao().updateFavoriteStatus(trackId, true)
    }

    override suspend fun removeFromFavorites(trackId: String) {
        appDataBase.trackDao().updateFavoriteStatus(trackId, false)
    }

    override suspend fun toggleFavorite(trackId: String) {
        val trackEntity = appDataBase.trackDao().getIdTracks(trackId)
        trackEntity?.let {
            appDataBase.trackDao().updateFavoriteStatus(trackId, !it.isFavorite)
        }
    }

    override suspend fun getFavoriteTracks(): Flow<List<TrackData>> =flow{
        val favorites = appDataBase.trackDao().getFavoriteTracks()
        emit(convertFromTracksEntity(favorites))
    }

    override suspend fun isTrackFavorite(trackId: String): Boolean {
        val trackEntity = appDataBase.trackDao().getIdTracks(trackId)
        return trackEntity?.isFavorite ?: false
    }

    override suspend fun getTrackById(trackId: String): TrackData? {
        val trackEntity = appDataBase.trackDao().getIdTracks(trackId)
        return trackEntity?.let { trackDbConvertor.map(it) }
    }

    private fun convertFromTracksEntity(tracks: List<TrackEntity>): List<TrackData>{
        return tracks.map { track -> trackDbConvertor.map(track)}
    }
    private fun convertFromTrackEntity(track: TrackData): TrackEntity{
        return trackDbConvertor.map(track)
    }
}