package com.example.mynewplaylist.legacy.domain.api

import com.example.mynewplaylist.legacy.domain.models.Track

interface TrackIntercator {
    fun searchTracks(expression:String,consumer:TrackConsumer)
    interface TrackConsumer{
        fun consume(foundTracks:List<Track>)
    }
}