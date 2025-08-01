package com.example.mynewplaylist.legacy.domain.api


interface MediaPlayerInteractor {
    fun startPlayer()
    fun pausePlayer()
    fun releasePlayer()
    fun getCurrentPosition():Int
    fun preparePlayer(playImage:Any,url:String,onPrepared:()->Unit,onCompletion:()->Unit)

}