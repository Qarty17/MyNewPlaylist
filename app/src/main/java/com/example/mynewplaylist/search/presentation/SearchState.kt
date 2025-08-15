package com.example.mynewplaylist.search.presentation

import com.example.mynewplaylist.search.domain.models.Track
import com.example.mynewplaylist.search.ui.PlaylistState

data class SearchState(var history: ArrayList<Track>,var state: PlaylistState?)
