package com.example.mynewplaylist.search.data

import com.example.mynewplaylist.common.Resource
import com.example.mynewplaylist.search.domain.api.TracksRepository
import com.example.mynewplaylist.search.domain.models.Track
import com.example.mynewplaylist.search.data.dto.PlaylistRequest
import com.example.mynewplaylist.search.data.dto.PlaylistResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class TracksRepositoryImpl(private val networkClient: NetworkClient): TracksRepository {
    override fun searchTracks(expression: String): Flow<Resource<List<Track>>> =flow {
        val response=networkClient.doRequest(PlaylistRequest(expression))
        when(response.resultCode){
            -1->emit(Resource.Error("not ethernet"))
            200->{
            with(response as PlaylistResponse) {
                val data=response.results.map {
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
                }
                emit(Resource.Success(data))
            }
        }
            else -> emit(Resource.Error("Ошибка сервера"))
        }
    }
}