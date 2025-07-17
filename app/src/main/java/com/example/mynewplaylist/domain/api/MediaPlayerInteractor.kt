package com.example.mynewplaylist.domain.api

interface MediaPlayerInteractor {
    fun startPlayer()
    fun pausePlayer()
    fun releasePlayer()
    fun getCurrentPosition():Int
    fun preparePlayer(playImage:Any,url:String)
}