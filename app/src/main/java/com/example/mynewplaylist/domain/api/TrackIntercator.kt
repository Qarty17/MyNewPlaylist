package com.example.mynewplaylist.domain.api

import com.example.mynewplaylist.domain.models.Track

interface TrackIntercator {
    fun searchTracks(expression:String,consumer:TrackConsumer)
    interface TrackConsumer{
        fun consume(foundTracks:List<Track>)
    }
}