package com.example.mynewplaylist.player.presentation

import android.media.MediaPlayer
import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.mynewplaylist.player.presentation.PlayerState
import kotlinx.coroutines.Runnable
import java.text.SimpleDateFormat
import java.util.Locale

class PlayerViewModel(private val url: String): ViewModel() {
    companion object{
        const val STATE_DEFAULT=0
        const val STATE_PREPARED=1
        const val STATE_PLAYING=2
        const val STATE_PAUSED=3
        fun getFactory(trackUrl:String): ViewModelProvider.Factory= viewModelFactory {
            initializer {
                PlayerViewModel(trackUrl)
            }
        }
    }

    private val mediaPlayer= MediaPlayer()
    private val handler: Handler = Handler(Looper.getMainLooper())

    private val playerProgressLiveData= MutableLiveData<PlayerState>(PlayerState(STATE_DEFAULT,"0:00"))
    fun observePlayerProgressLiveData(): LiveData<PlayerState> = playerProgressLiveData

    private val timerRunnable= Runnable {
        if (playerProgressLiveData.value?.player == STATE_PLAYING) {
            startTimerUpdate()
        }


    }
    fun onPause(){
        pausePlayer()
    }
    init {
        preparePlayer()
    }

    private fun startTimerUpdate(){
        //playerProgressLiveData.value?.progress= SimpleDateFormat("m:ss", Locale.getDefault()).format(mediaPlayer.currentPosition)
        playerProgressLiveData.postValue(PlayerState(STATE_PLAYING,SimpleDateFormat("m:ss", Locale.getDefault()).format(mediaPlayer.currentPosition)))
        handler.postDelayed(timerRunnable,200)

    }
    private fun preparePlayer(){
        mediaPlayer.setDataSource(url)
        mediaPlayer.prepareAsync()
        mediaPlayer.setOnPreparedListener {
            playerProgressLiveData.postValue(PlayerState(STATE_PREPARED,"0:00"))
            //playerProgressLiveData.value?.player=STATE_PREPARED

        }
        mediaPlayer.setOnCompletionListener {
            playerProgressLiveData.postValue(PlayerState(STATE_PREPARED,"0:00"))
            resetTimer()
        }

    }
    fun onPlayButtonClicked(){
        when(playerProgressLiveData.value?.player){
            STATE_PLAYING->{
                playerProgressLiveData.postValue(PlayerState(STATE_PAUSED,playerProgressLiveData.value?.progress!!))
                pausePlayer()
            }
            STATE_PREPARED,STATE_PAUSED->{
                startPlayer()
                playerProgressLiveData.postValue(PlayerState(STATE_PLAYING,playerProgressLiveData.value?.progress!!))
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        mediaPlayer.release()
        resetTimer()
    }
    private fun startPlayer(){
        mediaPlayer.start()

        playerProgressLiveData.value?.player=STATE_PLAYING
        startTimerUpdate()
    }
    private fun pausePlayer(){
        pauseTimer()
        mediaPlayer.pause()

        playerProgressLiveData.value?.player=STATE_PAUSED


    }
    private fun pauseTimer(){
        handler.removeCallbacks(timerRunnable)
    }
    private fun resetTimer(){

        handler.removeCallbacks (timerRunnable)
        playerProgressLiveData.value?.progress="0:00"
    }
}