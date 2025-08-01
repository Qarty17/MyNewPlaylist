package com.example.mynewplaylist.legacy.data


import android.media.MediaPlayer

import com.example.mynewplaylist.legacy.domain.api.MediaPlayerRepository



class MediaPlayerRepositoryImpl:MediaPlayerRepository {


    private var mediaPlayer= MediaPlayer()
    override fun startPlayer() {
        mediaPlayer.start()


    }

    override fun pausePlayer() {
        mediaPlayer.pause()

    }

    override fun releasePlayer() {
        mediaPlayer.release()
    }

    override fun getCurrentPosition():Int {
        return mediaPlayer.currentPosition
    }

    override fun preparePlayer(playImage:Any,url: String,onPrepared:()->Unit,onCompletion:()->Unit) {
        mediaPlayer.setDataSource(url)
        mediaPlayer.prepareAsync()
        mediaPlayer.setOnPreparedListener{
            onPrepared()

        }
        mediaPlayer.setOnCompletionListener {
            onCompletion()
        }
    }


}