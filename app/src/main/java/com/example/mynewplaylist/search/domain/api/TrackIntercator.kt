package com.example.mynewplaylist.search.domain.api

import com.example.mynewplaylist.search.domain.models.Track

interface TrackIntercator {
    fun searchTracks(expression:String,consumer:TrackConsumer)
    interface TrackConsumer{
        fun consume(foundTracks:List<Track>?, errorMessage: String?)
    }
}