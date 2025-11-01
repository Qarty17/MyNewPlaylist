package com.example.mynewplaylist.common.di

import com.example.mynewplaylist.media.domain.db.HistoryMediaInteractor
import com.example.mynewplaylist.media.domain.db.HistoryMediaRepository
import com.example.mynewplaylist.media.domain.impl.HistoryMediaInteractorImpl
import com.example.mynewplaylist.media.presentation.FavoriteTracksViewModel
import com.example.mynewplaylist.media.presentation.MediaViewModel
import com.example.mynewplaylist.media.presentation.NewPlaylistViewModel
import com.example.mynewplaylist.player.presentation.PlayerViewModel
import com.example.mynewplaylist.search.presentation.PlaylistViewModel
import com.example.mynewplaylist.settings.presentation.SettingsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule=module{
    viewModel {
        PlaylistViewModel(get(),get())
    }
    viewModel{(url: String)->
        PlayerViewModel(get(),url, get())
    }
    viewModel {
        SettingsViewModel(get(),get())
    }
    viewModel{
        NewPlaylistViewModel()
    }
    viewModel{
        FavoriteTracksViewModel()
    }
    viewModel{
        MediaViewModel()
    }
}