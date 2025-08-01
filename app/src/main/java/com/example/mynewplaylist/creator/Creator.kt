package com.example.mynewplaylist.creator

import android.content.Context
import com.example.mynewplaylist.legacy.data.HistoryRepositoryImpl
import com.example.mynewplaylist.legacy.data.MediaPlayerRepositoryImpl
import com.example.mynewplaylist.legacy.data.SwitchRepositoryImpl
import com.example.mynewplaylist.legacy.data.ThemeRepositoryImpl
import com.example.mynewplaylist.legacy.data.TracksRepositoryImpl
import com.example.mynewplaylist.legacy.data.network.RetrofitNetworkClient
import com.example.mynewplaylist.legacy.domain.api.HistoryInteractor
import com.example.mynewplaylist.legacy.domain.api.HistoryRepository
import com.example.mynewplaylist.legacy.domain.api.MediaPlayerInteractor
import com.example.mynewplaylist.legacy.domain.api.MediaPlayerRepository
import com.example.mynewplaylist.legacy.domain.api.SwitchInteractor
import com.example.mynewplaylist.legacy.domain.api.SwitchRepository
import com.example.mynewplaylist.legacy.domain.api.ThemeInteractor
import com.example.mynewplaylist.legacy.domain.api.ThemeRepository
import com.example.mynewplaylist.legacy.domain.api.TrackIntercator
import com.example.mynewplaylist.legacy.domain.api.TracksRepository
import com.example.mynewplaylist.legacy.domain.impl.HistoryInteractorImpl
import com.example.mynewplaylist.legacy.domain.impl.MediaPlayerInteractorImpl
import com.example.mynewplaylist.legacy.domain.impl.SwitchInteractorImpl
import com.example.mynewplaylist.legacy.domain.impl.ThemeInteractorImpl
import com.example.mynewplaylist.legacy.domain.impl.TrackInteractorImpl
import com.example.mynewplaylist.sharing.data.impl.ExternalNavigatorImpl
import com.example.mynewplaylist.sharing.domain.ExternalNavigator
import com.example.mynewplaylist.sharing.domain.SharingInteractor
import com.example.mynewplaylist.sharing.domain.impl.SharingInteractorImpl

object Creator {
    private fun getTracksRepository(): TracksRepository {
        return TracksRepositoryImpl(RetrofitNetworkClient())
    }
    fun provideTrackInteractor(): TrackIntercator {
        return TrackInteractorImpl(getTracksRepository())
    }
    private fun getHistoryRepository(context: Context): HistoryRepository {
        return HistoryRepositoryImpl(context)
    }
    fun provideHistoryInteractor(context: Context): HistoryInteractor {
        return HistoryInteractorImpl(getHistoryRepository(context))
    }
    private fun getSwitchRepository(context: Context): SwitchRepository {
        return SwitchRepositoryImpl(context)
    }
    fun provideSwitchInteractor(context: Context): SwitchInteractor {
        return SwitchInteractorImpl(getSwitchRepository(context))
    }
    private fun getThemeRepository(context: Context): ThemeRepository {
        return ThemeRepositoryImpl(context)
    }
    fun provideThemeInteractor(context: Context): ThemeInteractor {
        return ThemeInteractorImpl(getThemeRepository(context))
    }
    private fun getMediaPlayerRepository(): MediaPlayerRepository {
        return MediaPlayerRepositoryImpl()
    }
    fun provideMediaPlayerInteractor(): MediaPlayerInteractor {
        return MediaPlayerInteractorImpl(getMediaPlayerRepository())
    }
    private fun getExternalNavigator(): ExternalNavigator{
        return ExternalNavigatorImpl()
    }
    fun provideSharingInteractor(): SharingInteractor{
        return SharingInteractorImpl(getExternalNavigator())
    }
}