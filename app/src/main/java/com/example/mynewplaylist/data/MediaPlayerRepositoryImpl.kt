package com.example.mynewplaylist.data


import android.media.MediaPlayer
import android.widget.ImageButton
import com.example.mynewplaylist.domain.api.MediaPlayerRepository


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

    override fun preparePlayer(playImage:Any,url: String) {
        mediaPlayer.setDataSource(url)
        mediaPlayer.prepareAsync()
        mediaPlayer.setOnPreparedListener{
            if (playImage is ImageButton){
                playImage.isEnabled=true

            }
        }
        mediaPlayer.setOnCompletionListener {

        }
    }
}