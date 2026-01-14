package com.example.mynewplaylist.common.di


import com.example.mynewplaylist.media.data.HistoryMediaRepositoryImpl
import com.example.mynewplaylist.media.data.converters.TrackDbConvertor
import com.example.mynewplaylist.media.domain.db.HistoryMediaRepository

import com.example.mynewplaylist.search.data.HistoryRepositoryImpl
import com.example.mynewplaylist.search.data.TracksRepositoryImpl
import com.example.mynewplaylist.search.domain.api.HistoryRepository
import com.example.mynewplaylist.search.domain.api.TracksRepository
import com.example.mynewplaylist.settings.data.impl.SwitchRepositoryImpl
import com.example.mynewplaylist.settings.data.impl.ThemeRepositoryImpl
import com.example.mynewplaylist.settings.domain.api.SwitchRepository
import com.example.mynewplaylist.settings.domain.api.ThemeRepository
import com.example.mynewplaylist.sharing.data.impl.ExternalNavigatorImpl
import com.example.mynewplaylist.sharing.domain.ExternalNavigator
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val repositoryModule= module{
    single < HistoryMediaRepository> {
        HistoryMediaRepositoryImpl(get(),get())
    }
    factory { TrackDbConvertor() }
    single<TracksRepository> {
        TracksRepositoryImpl(get())
    }
    single <HistoryRepository> {
        HistoryRepositoryImpl(get())
    }
    single <ExternalNavigator> {
        ExternalNavigatorImpl(androidContext())
    }
    single<ThemeRepository> {
        ThemeRepositoryImpl(get())
    }
    single<SwitchRepository> {
        SwitchRepositoryImpl(get())
    }
}