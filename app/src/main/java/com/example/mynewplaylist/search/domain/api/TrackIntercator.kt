package com.example.mynewplaylist.search.domain.api

import com.example.mynewplaylist.search.domain.models.Track
import kotlinx.coroutines.flow.Flow

interface TrackIntercator {
    fun searchTracks(expression:String): Flow<Pair<List<Track>?, String?>>
}