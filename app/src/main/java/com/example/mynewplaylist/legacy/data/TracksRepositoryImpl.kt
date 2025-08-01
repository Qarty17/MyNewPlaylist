package com.example.mynewplaylist.legacy.data


import com.example.mynewplaylist.legacy.data.dto.PlaylistRequest
import com.example.mynewplaylist.legacy.data.dto.PlaylistResponse
import com.example.mynewplaylist.legacy.domain.api.TracksRepository
import com.example.mynewplaylist.legacy.domain.models.Track


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