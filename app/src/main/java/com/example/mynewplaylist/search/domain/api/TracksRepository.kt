package com.example.mynewplaylist.search.domain.api

import com.example.mynewplaylist.common.Resource
import com.example.mynewplaylist.search.domain.models.Track
import kotlinx.coroutines.flow.Flow

interface TracksRepository {
    fun searchTracks(expression:String): Flow<Resource<List<Track>>>
}