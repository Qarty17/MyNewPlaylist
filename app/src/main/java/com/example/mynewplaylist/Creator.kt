package com.example.mynewplaylist

import android.content.Context
import com.example.mynewplaylist.data.HistoryRepositoryImpl
import com.example.mynewplaylist.data.MediaPlayerRepositoryImpl
import com.example.mynewplaylist.data.SwitchRepositoryImpl
import com.example.mynewplaylist.data.ThemeRepositoryImpl
import com.example.mynewplaylist.data.TracksRepositoryImpl
import com.example.mynewplaylist.data.network.RetrofitNetworkClient
import com.example.mynewplaylist.domain.api.HistoryInteractor
import com.example.mynewplaylist.domain.api.HistoryRepository
import com.example.mynewplaylist.domain.api.MediaPlayerInteractor
import com.example.mynewplaylist.domain.api.MediaPlayerRepository
import com.example.mynewplaylist.domain.api.SwitchInteractor
import com.example.mynewplaylist.domain.api.SwitchRepository
import com.example.mynewplaylist.domain.api.ThemeInteractor
import com.example.mynewplaylist.domain.api.ThemeRepository
import com.example.mynewplaylist.domain.api.TrackIntercator
import com.example.mynewplaylist.domain.api.TracksRepository
import com.example.mynewplaylist.domain.impl.HistoryInteractorImpl
import com.example.mynewplaylist.domain.impl.MediaPlayerInteractorImpl
import com.example.mynewplaylist.domain.impl.SwitchInteractorImpl
import com.example.mynewplaylist.domain.impl.ThemeInteractorImpl
import com.example.mynewplaylist.domain.impl.TrackInteractorImpl

object Creator {
    private fun getTracksRepository():TracksRepository{
        return TracksRepositoryImpl(RetrofitNetworkClient())
    }
    fun provideTrackInteractor():TrackIntercator{
        return TrackInteractorImpl(getTracksRepository())
    }
    private fun getHistoryRepository(context: Context):HistoryRepository{
        return HistoryRepositoryImpl(context)
    }
    fun provideHistoryInteractor(context: Context):HistoryInteractor{
        return HistoryInteractorImpl(getHistoryRepository(context))
    }
    private fun getSwitchRepository(context: Context):SwitchRepository{
        return SwitchRepositoryImpl(context)
    }
    fun provideSwitchInteractor(context: Context):SwitchInteractor{
        return SwitchInteractorImpl(getSwitchRepository(context))
    }
    private fun getThemeRepository(context: Context):ThemeRepository{
        return ThemeRepositoryImpl(context)
    }
    fun provideThemeInteractor(context: Context):ThemeInteractor{
        return ThemeInteractorImpl(getThemeRepository(context))
    }
    private fun getMediaPlayerRepository():MediaPlayerRepository{
        return MediaPlayerRepositoryImpl()
    }
    fun provideMediaPlayerInteractor():MediaPlayerInteractor{
        return MediaPlayerInteractorImpl(getMediaPlayerRepository())
    }
}