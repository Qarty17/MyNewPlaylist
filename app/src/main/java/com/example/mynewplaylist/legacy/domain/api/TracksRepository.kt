package com.example.mynewplaylist.legacy.domain.api

import com.example.mynewplaylist.legacy.domain.models.Track

interface TracksRepository {
    fun searchTracks(expression:String):List<Track>
}