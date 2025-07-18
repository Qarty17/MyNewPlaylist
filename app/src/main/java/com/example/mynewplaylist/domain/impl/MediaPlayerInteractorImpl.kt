package com.example.mynewplaylist.domain.impl

import com.example.mynewplaylist.domain.api.MediaPlayerInteractor
import com.example.mynewplaylist.domain.api.MediaPlayerRepository

class MediaPlayerInteractorImpl(private val mediaPlayerRepository: MediaPlayerRepository):MediaPlayerInteractor {
    override fun startPlayer() {
        mediaPlayerRepository.startPlayer()
    }

    override fun pausePlayer() {
        mediaPlayerRepository.pausePlayer()
    }

    override fun releasePlayer() {
        mediaPlayerRepository.releasePlayer()
    }

    override fun getCurrentPosition():Int {
        return mediaPlayerRepository.getCurrentPosition()
    }

    override fun preparePlayer(playImage: Any, url: String,onPrepared:()->Unit,onCompletion:()->Unit) {
        mediaPlayerRepository.preparePlayer(playImage,url,onPrepared,onCompletion)
    }


}