package com.example.mynewplaylist.search.ui

import com.example.mynewplaylist.search.domain.models.Track

sealed interface PlaylistState {
    object Loading: PlaylistState
    data class Content(val tracks: ArrayList<Track>): PlaylistState
    data class Error(val errorMessage: String): PlaylistState
    data class Empty(val message:String): PlaylistState
}