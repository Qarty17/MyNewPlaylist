package com.example.mynewplaylist.media.data

import android.R.attr.track
import com.example.mynewplaylist.media.data.converters.TrackDbConvertor
import com.example.mynewplaylist.media.data.db.AppDataBase
import com.example.mynewplaylist.media.data.db.entity.TrackEntity
import com.example.mynewplaylist.media.domain.db.HistoryMediaRepository
import com.example.mynewplaylist.search.domain.models.Track
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class HistoryMediaRepositoryImpl(
    private val appDataBase: AppDataBase,
    private val trackDbConvertor: TrackDbConvertor
): HistoryMediaRepository {
    override suspend fun insertTrack(track: Track)   {

        val track=convertFromTrackEntity(track)
        appDataBase.trackDao().insertTracks(track)
    }

    override suspend fun deleteTrack(track: Track) {
        val track=convertFromTrackEntity(track)
        appDataBase.trackDao().deleteTracks(track)
    }

    override suspend fun historyTracks(): Flow<List<Track>> = flow{
        val tracks=appDataBase.trackDao().getTracks()
        emit(convertFromTracksEntity(tracks))
    }

    override suspend fun getIdTracks(trackId: Long): Flow<Long> {

        return if(trackId==appDataBase.trackDao().getIdTracks(trackId).trackId.toLong()){
            flow { trackId }
        }else{
            return flow { 0 }
        }
    }

    private fun convertFromTracksEntity(tracks: List<TrackEntity>): List<Track>{
        return tracks.map { track -> trackDbConvertor.map(track)}
    }
    private fun convertFromTrackEntity(track: Track): TrackEntity{
        return trackDbConvertor.map(track)
    }
}