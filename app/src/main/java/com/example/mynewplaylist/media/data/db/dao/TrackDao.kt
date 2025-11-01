package com.example.mynewplaylist.media.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mynewplaylist.media.data.db.entity.TrackEntity

@Dao
interface TrackDao {
    @Insert(entity = TrackEntity::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTracks(track:TrackEntity)
    @Delete(entity = TrackEntity::class)
    suspend fun deleteTracks(tracks: TrackEntity)
    @Query("SELECT * FROM track_table")
    suspend fun getTracks(): List<TrackEntity>
    @Query("SELECT * FROM track_table WHERE trackId = :trackId")
    suspend fun getIdTracks(trackId:Long): TrackEntity

}