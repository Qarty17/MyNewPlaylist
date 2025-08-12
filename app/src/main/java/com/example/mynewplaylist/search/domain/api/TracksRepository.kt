package com.example.mynewplaylist.search.domain.api

import com.example.mynewplaylist.creator.Resource
import com.example.mynewplaylist.search.domain.models.Track

interface TracksRepository {
    fun searchTracks(expression:String): Resource<List<Track>>
}