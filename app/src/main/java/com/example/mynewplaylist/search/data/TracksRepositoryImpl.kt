package com.example.mynewplaylist.search.data

import com.example.mynewplaylist.common.Resource
import com.example.mynewplaylist.search.domain.api.TracksRepository
import com.example.mynewplaylist.search.domain.models.Track
import com.example.mynewplaylist.search.data.dto.PlaylistRequest
import com.example.mynewplaylist.search.data.dto.PlaylistResponse

class TracksRepositoryImpl(private val networkClient: NetworkClient): TracksRepository {
    override fun searchTracks(expression: String): Resource<List<Track>> {
        val response=networkClient.doRequest(PlaylistRequest(expression))
        return when(response.resultCode){
            -1->return Resource.Error("not ethernet")
            200->{
            return Resource.Success((response as PlaylistResponse).results.map {
                Track(
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
            })
        }
            else -> return Resource.Error("Ошибка сервера")
        }
    }
}