package com.example.mynewplaylist.player.presentation

sealed class FavoriteState(val isFavorite: Boolean) {
    class IsNotFavorite: FavoriteState(false)
    class IsFavorite: FavoriteState(true)

}