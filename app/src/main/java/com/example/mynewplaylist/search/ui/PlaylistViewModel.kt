package com.example.mynewplaylist.search.ui


import android.content.Context
import android.os.Handler
import android.os.Looper
import android.os.SystemClock

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.mynewplaylist.creator.Creator
import com.example.mynewplaylist.search.domain.api.TrackIntercator
import com.example.mynewplaylist.search.domain.models.Track

import com.example.mynewplaylist.settings.ui.App
import kotlinx.coroutines.Runnable

class PlaylistViewModel(context: Context): ViewModel() {

    private var latestSearchText: String? = null
    private val stateLiveData= MutableLiveData<PlaylistState>()
    fun observeState(): LiveData<PlaylistState> = stateLiveData
    private val provider=Creator.provideTrackInteractor(context)
    private val handler:Handler =Handler(Looper.getMainLooper())
    fun searchCreate(newSearchText:String){
        if (newSearchText.isNotEmpty()){
            renderState(
                PlaylistState.Loading
            )
        }
        provider.searchTracks(newSearchText, object :TrackIntercator.TrackConsumer{
            override fun consume(foundTracks: List<Track>?,errorMessage: String?) {
                handler.post{
                    val tracks=arrayListOf<Track>()
                    if (foundTracks?.isNotEmpty() == true) {
                        tracks.addAll(foundTracks)
                    }
                    when{
                        errorMessage!=null->{
                            renderState(
                                PlaylistState.Error(errorMessage="not Internet")
                            )
                        }
                        tracks.isEmpty()->{
                            renderState(
                                PlaylistState.Empty("not found")
                            )
                        }
                        else->{
                            renderState(
                                PlaylistState.Content(tracks)
                            )
                        }
                    }


                }

            }
        })
    }
    fun searchDebounce(changedText: String){
        if(latestSearchText==changedText)
        {
            return
        }
        handler.removeCallbacksAndMessages(SEARCH_REQUEST_TOKEN)
        this.latestSearchText=changedText
        var searchRunnable= Runnable{searchCreate(changedText)}
        val postTime= SystemClock.uptimeMillis()+2000L
        handler.postAtTime(searchRunnable,SEARCH_REQUEST_TOKEN,postTime)


    }

    private fun renderState(state: PlaylistState) {
        stateLiveData.postValue(state)

    }

    override fun onCleared() {
        super.onCleared()
        handler.removeCallbacksAndMessages(SEARCH_REQUEST_TOKEN)
    }
    fun clearHandler(){
        handler.removeCallbacksAndMessages(SEARCH_REQUEST_TOKEN)
        this.latestSearchText=null
    }

    companion object{
        val SEARCH_REQUEST_TOKEN = Any()
        fun getFactory(): ViewModelProvider.Factory= viewModelFactory {
            initializer {
                val app= (this[APPLICATION_KEY]as App)
                PlaylistViewModel(app)
            }
        }
    }
}