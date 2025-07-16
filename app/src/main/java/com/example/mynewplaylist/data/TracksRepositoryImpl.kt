package com.example.mynewplaylist.data


import com.example.mynewplaylist.data.dto.PlaylistRequest
import com.example.mynewplaylist.data.dto.PlaylistResponse
import com.example.mynewplaylist.domain.api.TracksRepository
import com.example.mynewplaylist.domain.models.Track
import java.text.SimpleDateFormat

import java.util.Locale

class TracksRepositoryImpl(private val networkClient: NetworkClient):TracksRepository {
    override fun searchTracks(expression: String): List<Track> {
        val response=networkClient.doRequest(PlaylistRequest(expression))
        if (response.resultCode==200){
            return (response as PlaylistResponse).results.map { Track(
                it.trackName,
                it.artistName,
                it.trackTimeMillis,
                it.artworkUrl100,
                it.trackId,
                it.collectionName,
                it.releaseDate,
                it.primaryGenreName,
                it.country,
                it.previewUrl,
                )
            }
        }else{
            return emptyList()
        }
    }
}