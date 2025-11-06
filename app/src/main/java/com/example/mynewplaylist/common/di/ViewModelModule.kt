package com.example.mynewplaylist.common.di

import com.example.mynewplaylist.media.presentation.MediaViewModel
import com.example.mynewplaylist.media.presentation.NewPlaylistViewModel
import com.example.mynewplaylist.player.presentation.PlayerViewModel
import com.example.mynewplaylist.search.domain.models.Track
import com.example.mynewplaylist.search.presentation.PlaylistViewModel
import com.example.mynewplaylist.settings.presentation.SettingsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule=module{
    viewModel {
        PlaylistViewModel(get(),get())
    }
    viewModel{(url: String,track:Track)->
        PlayerViewModel(get(),url,track,get())
    }
    viewModel {
        SettingsViewModel(get(),get())
    }
    viewModel{
        NewPlaylistViewModel(get())
    }

    viewModel{
        MediaViewModel()
    }
}