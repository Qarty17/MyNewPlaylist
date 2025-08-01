package com.example.mynewplaylist.legacy.domain.api

interface MediaPlayerRepository {

    fun startPlayer()
    fun pausePlayer()
    fun releasePlayer()
    fun getCurrentPosition():Int
    fun preparePlayer(playImage:Any,url: String,onPrepared:()->Unit,onCompletion:()->Unit)
}