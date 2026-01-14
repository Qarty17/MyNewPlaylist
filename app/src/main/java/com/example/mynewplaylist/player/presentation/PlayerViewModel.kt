package com.example.mynewplaylist.player.presentation

import android.R.attr.track
import android.media.MediaPlayer
import android.os.Handler
import android.os.Looper
import androidx.annotation.Nullable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.mynewplaylist.media.domain.db.HistoryMediaInteractor
import com.example.mynewplaylist.media.domain.models.TrackData

import kotlinx.coroutines.Job
import kotlinx.coroutines.Runnable
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale

class PlayerViewModel(private val mediaPlayer: MediaPlayer, private val url: String, private val track: TrackData, private val historyMediaInteractor: HistoryMediaInteractor): ViewModel() {
    private var timerJob:Job? = null
    private val playerState= MutableLiveData<PlayerState2>(PlayerState2.Default())
    private val favoriteState= MutableLiveData<FavoriteState>(FavoriteState.IsNotFavorite())
    fun observeFavoriteState(): LiveData<FavoriteState> = favoriteState
    fun observePlayerState(): LiveData<PlayerState2> = playerState
    init {
        preparePlayer()
        loadTrackStateFromDatabase()
    }
    fun onPause(){
        pausePlayer()
    }
    override fun onCleared() {
        super.onCleared()
        releasePlayer()

    }

    private fun preparePlayer(){
        mediaPlayer.setDataSource(url)
        mediaPlayer.prepareAsync()
        mediaPlayer.setOnPreparedListener {
            playerState.postValue(PlayerState2.Prepared())

        }
        mediaPlayer.setOnCompletionListener {
            timerJob?.cancel()
            playerState.postValue(PlayerState2.Prepared())

        }

    }

    fun onPlayButtonClicked(){
        when(playerState.value){
            is PlayerState2.Playing->{
                pausePlayer()
            }
            is PlayerState2.Prepared,is PlayerState2.Paused->{
                startPlayer()
            }
            else -> {}
        }
    }
    private fun startPlayer(){
        mediaPlayer.start()
        playerState.postValue(PlayerState2.Playing(getCurrentPlayerPosition()))
        startTimer()
    }
    private fun pausePlayer(){
        timerJob?.cancel()
        mediaPlayer.pause()
        playerState.postValue(PlayerState2.Paused(getCurrentPlayerPosition()))


    }
    private fun releasePlayer(){
        mediaPlayer.stop()
        mediaPlayer.release()
        playerState.postValue(PlayerState2.Default())
    }
    private fun startTimer(){
        timerJob?.cancel()
        timerJob=viewModelScope.launch {
            while (mediaPlayer.isPlaying){
                delay(300L)
                playerState.postValue(PlayerState2.Playing(getCurrentPlayerPosition()))
            }
        }
    }
    private fun getCurrentPlayerPosition(): String{
        return SimpleDateFormat("mm:ss", Locale.getDefault()).format(mediaPlayer.currentPosition)?:"00:00"
    }
    private fun loadTrackStateFromDatabase(){
        viewModelScope.launch {
            try{
                val dbTrack=historyMediaInteractor.getTrackById(track.trackId)
                if (dbTrack!=null){
                    track.isFavorite=dbTrack.isFavorite
                }else{
                    track.isFavorite=false
                }
                updateFavoriteState(track.isFavorite)
            }catch (e: Exception){
                track.isFavorite=false
                updateFavoriteState(false)
            }
        }
    }
    fun onFavoriteClicked(){
        viewModelScope.launch {
            try {
                val updatedTrack=track.copy(isFavorite = track.isFavorite)

                if(historyMediaInteractor.getTrackById(track.trackId)==null){

                    historyMediaInteractor.insertTrack(updatedTrack)
                    historyMediaInteractor.addToFavorites(track.trackId)
                    updateFavoriteState(true)
                }
                else{

                    historyMediaInteractor.deleteTrack(updatedTrack)
                    historyMediaInteractor.removeFromFavorites(track.trackId)
                    updateFavoriteState(false)
                }
            }catch (e: Exception){
                updateFavoriteState(track.isFavorite)
            }
        }

    }
    private fun updateFavoriteState(isFavorite: Boolean){
        favoriteState.value=if(isFavorite){
            FavoriteState.IsFavorite()
        }else{
            FavoriteState.IsNotFavorite()
        }
    }
}