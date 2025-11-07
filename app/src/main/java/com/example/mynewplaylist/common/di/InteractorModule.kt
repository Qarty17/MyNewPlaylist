package com.example.mynewplaylist.common.di

import com.example.mynewplaylist.search.domain.api.HistoryInteractor
import com.example.mynewplaylist.search.domain.api.TrackIntercator
import com.example.mynewplaylist.search.domain.impl.HistoryInteractorImpl
import com.example.mynewplaylist.search.domain.impl.TrackInteractorImpl
import com.example.mynewplaylist.settings.domain.api.SwitchInteractor
import com.example.mynewplaylist.settings.domain.api.ThemeInteractor
import com.example.mynewplaylist.settings.domain.impl.SwitchInteractorImpl
import com.example.mynewplaylist.settings.domain.impl.ThemeInteractorImpl
import com.example.mynewplaylist.sharing.domain.SharingInteractor
import com.example.mynewplaylist.sharing.domain.impl.SharingInteractorImpl
import org.koin.dsl.module

val interactorModule = module{
    single<TrackIntercator> {
        TrackInteractorImpl(get())
    }
    single<HistoryInteractor>{
        HistoryInteractorImpl(get())
    }
    single<SwitchInteractor> {
        SwitchInteractorImpl(get())
    }
    single<SharingInteractor> {
        SharingInteractorImpl(get())
    }
    single<ThemeInteractor> {
        ThemeInteractorImpl(get())
    }
}