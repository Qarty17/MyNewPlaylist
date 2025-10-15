package com.example.mynewplaylist.player.presentation

sealed class PlayerState2(val isPlayButtonEnabled: Boolean,val isPlayButtonPlaying: Boolean,val progress: String,) {

    class Default : PlayerState2(false,false, "00:00")

    class Prepared : PlayerState2(true,false, "00:00")

    class Playing(progress: String) : PlayerState2(true,true, progress)

    class Paused(progress: String) : PlayerState2(true,false, progress)
}