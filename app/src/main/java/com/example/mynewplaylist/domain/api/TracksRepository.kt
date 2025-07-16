package com.example.mynewplaylist.domain.api

import com.example.mynewplaylist.data.dto.TrackDto
import com.example.mynewplaylist.domain.models.Track

interface TracksRepository {
    fun searchTracks(expression:String):List<Track>
}