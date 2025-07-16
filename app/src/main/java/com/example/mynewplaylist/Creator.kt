package com.example.mynewplaylist

import com.example.mynewplaylist.data.TracksRepositoryImpl
import com.example.mynewplaylist.data.network.RetrofitNetworkClient
import com.example.mynewplaylist.domain.api.TrackIntercator
import com.example.mynewplaylist.domain.api.TracksRepository
import com.example.mynewplaylist.domain.impl.TrackInteractorImpl

object Creator {
    private fun getTracksRepository():TracksRepository{
        return TracksRepositoryImpl(RetrofitNetworkClient())
    }
    fun provideTrackInteractor():TrackIntercator{
        return TrackInteractorImpl(getTracksRepository())
    }
}