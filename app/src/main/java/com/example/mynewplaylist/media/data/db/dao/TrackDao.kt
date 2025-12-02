package com.example.mynewplaylist.media.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mynewplaylist.media.data.db.entity.TrackEntity

@Dao
interface TrackDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTracks(track:TrackEntity)
    @Delete(entity = TrackEntity::class)

    suspend fun deleteTracks(tracks: TrackEntity)
    @Query("SELECT * FROM track_table")
    suspend fun getTracks(): List<TrackEntity>
    @Query("SELECT * FROM track_table WHERE trackId = :trackId")
    suspend fun getIdTracks(trackId: String): TrackEntity?

    @Query("SELECT * FROM track_table WHERE isFavorite = 1")
    suspend fun getFavoriteTracks(): List<TrackEntity>

    @Query("UPDATE track_table SET isFavorite = :isFavorite WHERE trackId = :trackId")
    suspend fun updateFavoriteStatus(trackId: String, isFavorite: Boolean)
}