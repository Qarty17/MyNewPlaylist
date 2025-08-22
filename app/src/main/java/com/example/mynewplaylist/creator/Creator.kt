package com.example.mynewplaylist.creator

import android.app.Application
import android.content.Context
import com.example.mynewplaylist.common.data.storage.PrefsStorageClient
import com.example.mynewplaylist.search.data.HistoryRepositoryImpl
import com.example.mynewplaylist.settings.data.impl.SwitchRepositoryImpl
import com.example.mynewplaylist.settings.data.impl.ThemeRepositoryImpl
import com.example.mynewplaylist.search.data.TracksRepositoryImpl
import com.example.mynewplaylist.search.data.network.RetrofitNetworkClient
import com.example.mynewplaylist.search.domain.api.HistoryInteractor
import com.example.mynewplaylist.search.domain.api.HistoryRepository
import com.example.mynewplaylist.settings.domain.api.SwitchInteractor
import com.example.mynewplaylist.settings.domain.api.SwitchRepository
import com.example.mynewplaylist.settings.domain.api.ThemeInteractor
import com.example.mynewplaylist.settings.domain.api.ThemeRepository
import com.example.mynewplaylist.search.domain.api.TrackIntercator
import com.example.mynewplaylist.search.domain.api.TracksRepository
import com.example.mynewplaylist.search.domain.impl.HistoryInteractorImpl
import com.example.mynewplaylist.settings.domain.impl.SwitchInteractorImpl
import com.example.mynewplaylist.settings.domain.impl.ThemeInteractorImpl
import com.example.mynewplaylist.search.domain.impl.TrackInteractorImpl
import com.example.mynewplaylist.search.domain.models.Track
import com.example.mynewplaylist.sharing.data.impl.ExternalNavigatorImpl
import com.example.mynewplaylist.sharing.domain.ExternalNavigator
import com.example.mynewplaylist.sharing.domain.SharingInteractor
import com.example.mynewplaylist.sharing.domain.impl.SharingInteractorImpl
import com.google.gson.reflect.TypeToken

//object Creator {
//    private lateinit var application: Application
//    fun initApplication(application: Application){
//        this.application=application
//    }
//    private fun getTracksRepository(): TracksRepository {
//        return TracksRepositoryImpl(RetrofitNetworkClient(application))
//    }
//    fun provideTrackInteractor(): TrackIntercator {
//        return TrackInteractorImpl(getTracksRepository())
//    }
//    private fun getHistoryRepository(): HistoryRepository {
//        return HistoryRepositoryImpl(PrefsStorageClient<ArrayList<Track>>(
//            application,
//            "history",
//            object : TypeToken<ArrayList<Track>>(){}.type))
//    }
//    fun provideHistoryInteractor(): HistoryInteractor {
//        return HistoryInteractorImpl(getHistoryRepository())
//    }
//    private fun getSwitchRepository(context: Context): SwitchRepository {
//        return SwitchRepositoryImpl(context)
//    }
//    fun provideSwitchInteractor(context: Context): SwitchInteractor {
//        return SwitchInteractorImpl(getSwitchRepository(context))
//    }
//    private fun getThemeRepository(context: Context): ThemeRepository {
//        return ThemeRepositoryImpl(context)
//    }
//    fun provideThemeInteractor(context: Context): ThemeInteractor {
//        return ThemeInteractorImpl(getThemeRepository(context))
//    }
//
//    private fun getExternalNavigator(context: Context): ExternalNavigator{
//        return ExternalNavigatorImpl(context)
//    }
//    fun provideSharingInteractor(context: Context): SharingInteractor{
//        return SharingInteractorImpl(getExternalNavigator(context))
//    }
//}