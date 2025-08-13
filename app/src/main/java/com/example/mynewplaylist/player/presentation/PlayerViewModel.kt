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
    private var newValue="0:00"
    private val mediaPlayer= MediaPlayer()
    private val handler: Handler = Handler(Looper.getMainLooper())
    private val playerStateLiveData= MutableLiveData(STATE_DEFAULT)
    fun observePlayerState(): LiveData<Int> = playerStateLiveData
    private val progressTimeLiveData= MutableLiveData("0:00")
    fun observeProgressTime(): LiveData<String> =progressTimeLiveData

    private val timerRunnable= Runnable {
        if (playerStateLiveData.value == STATE_PLAYING) {
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

        progressTimeLiveData.value= SimpleDateFormat("m:ss", Locale.getDefault()).format(mediaPlayer.currentPosition)
        handler.postDelayed(timerRunnable,200)

    }
    private fun preparePlayer(){
        mediaPlayer.setDataSource(url)
        mediaPlayer.prepareAsync()
        mediaPlayer.setOnPreparedListener {
            playerStateLiveData.value=STATE_PREPARED

        }
        mediaPlayer.setOnCompletionListener {
            playerStateLiveData.value=STATE_PREPARED
            resetTimer()
        }

    }
    fun onPlayButtonClicked(){
        when(playerStateLiveData.value){
            STATE_PLAYING->pausePlayer()
            STATE_PREPARED,STATE_PAUSED->startPlayer()
        }
    }

    override fun onCleared() {
        super.onCleared()
        mediaPlayer.release()
        resetTimer()
    }
    private fun startPlayer(){
        mediaPlayer.start()

        playerStateLiveData.value=STATE_PLAYING
        startTimerUpdate()
    }
    private fun pausePlayer(){
        pauseTimer()
        mediaPlayer.pause()
        playerStateLiveData.value=STATE_PAUSED

    }
    private fun pauseTimer(){
        handler.removeCallbacks(timerRunnable)
    }
    private fun resetTimer(){

        handler.removeCallbacks (timerRunnable)
        progressTimeLiveData.value="0:00"
    }
}