package com.example.mynewplaylist.creator

import android.content.Context
import com.example.mynewplaylist.search.data.HistoryRepositoryImpl
import com.example.mynewplaylist.legacy.data.MediaPlayerRepositoryImpl
import com.example.mynewplaylist.legacy.data.SwitchRepositoryImpl
import com.example.mynewplaylist.legacy.data.ThemeRepositoryImpl
import com.example.mynewplaylist.search.data.TracksRepositoryImpl
import com.example.mynewplaylist.search.data.network.RetrofitNetworkClient
import com.example.mynewplaylist.search.domain.api.HistoryInteractor
import com.example.mynewplaylist.search.domain.api.HistoryRepository
import com.example.mynewplaylist.legacy.domain.api.MediaPlayerInteractor
import com.example.mynewplaylist.legacy.domain.api.MediaPlayerRepository
import com.example.mynewplaylist.legacy.domain.api.SwitchInteractor
import com.example.mynewplaylist.legacy.domain.api.SwitchRepository
import com.example.mynewplaylist.legacy.domain.api.ThemeInteractor
import com.example.mynewplaylist.legacy.domain.api.ThemeRepository
import com.example.mynewplaylist.search.domain.api.TrackIntercator
import com.example.mynewplaylist.search.domain.api.TracksRepository
import com.example.mynewplaylist.search.domain.impl.HistoryInteractorImpl
import com.example.mynewplaylist.legacy.domain.impl.MediaPlayerInteractorImpl
import com.example.mynewplaylist.legacy.domain.impl.SwitchInteractorImpl
import com.example.mynewplaylist.legacy.domain.impl.ThemeInteractorImpl
import com.example.mynewplaylist.search.domain.impl.TrackInteractorImpl
import com.example.mynewplaylist.sharing.data.impl.ExternalNavigatorImpl
import com.example.mynewplaylist.sharing.domain.ExternalNavigator
import com.example.mynewplaylist.sharing.domain.SharingInteractor
import com.example.mynewplaylist.sharing.domain.impl.SharingInteractorImpl

object Creator {
    private fun getTracksRepository(context: Context): TracksRepository {
        return TracksRepositoryImpl(RetrofitNetworkClient(context))
    }
    fun provideTrackInteractor(context: Context): TrackIntercator {
        return TrackInteractorImpl(getTracksRepository(context))
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
    private fun getExternalNavigator(context: Context): ExternalNavigator{
        return ExternalNavigatorImpl(context)
    }
    fun provideSharingInteractor(context: Context): SharingInteractor{
        return SharingInteractorImpl(getExternalNavigator(context))
    }
}