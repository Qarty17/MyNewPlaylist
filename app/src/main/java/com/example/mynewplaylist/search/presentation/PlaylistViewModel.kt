package com.example.mynewplaylist.search.presentation


import android.os.Handler
import android.os.Looper
import android.os.SystemClock
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mynewplaylist.creator.Creator
import com.example.mynewplaylist.search.domain.api.TrackIntercator
import com.example.mynewplaylist.search.domain.models.Track
import com.example.mynewplaylist.search.ui.PlaylistState
import kotlinx.coroutines.Runnable

class PlaylistViewModel(): ViewModel() {
    val historyProvider= Creator.provideHistoryInteractor()
    private var latestSearchText: String? = null

    private val searchLiveData= MutableLiveData(SearchState(historyProvider.getHistory(),PlaylistState.Content(arrayListOf())))
    fun observeSearch(): LiveData<SearchState> = searchLiveData
    private val provider= Creator.provideTrackInteractor()
    private var isClickAllowed = true
    private val handler: Handler = Handler(Looper.getMainLooper())

    fun searchCreate(newSearchText:String){
        if (newSearchText.isNotEmpty()){
            renderState(
                PlaylistState.Loading
            )
        }
        provider.searchTracks(newSearchText, object : TrackIntercator.TrackConsumer{
            override fun consume(foundTracks: List<Track>?, errorMessage: String?) {
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
    fun getHistory(): ArrayList<Track>{
        return historyProvider.getHistory()
    }
    fun clearHistory(){
        historyProvider.clearHistory()
    }
    fun onTrackClick(track: Track) {
        searchLiveData.value?.history=getHistory()
        searchLiveData.value?.history?.removeAll { it.trackId == track.trackId }
        searchLiveData.value?.history?.add(0, track)


        searchLiveData.value?.history?.size.let {
            if (it != null) {
                if (it > 10) {
                    searchLiveData.value?.history?.subList(10, searchLiveData.value?.history?.size!!)!!.clear()
                }
            }
        }
        historyProvider.saveHistory(searchLiveData.value?.history!!)
    }
    fun searchDebounce(changedText: String){
        if(latestSearchText==changedText)
        {
            return
        }
        handler.removeCallbacksAndMessages(SEARCH_REQUEST_TOKEN)
        this.latestSearchText=changedText
        var searchRunnable= Runnable { searchCreate(changedText) }
        val postTime= SystemClock.uptimeMillis()+2000L
        handler.postAtTime(searchRunnable,SEARCH_REQUEST_TOKEN,postTime)


    }

    private fun renderState(state: PlaylistState) {
        searchLiveData.postValue(SearchState(searchLiveData.value?.history ?: arrayListOf(),state))
        //stateLiveData.postValue(state)

    }
    fun clickDebounce():Boolean{
        val current=isClickAllowed
        if(isClickAllowed){
            isClickAllowed=false
            handler.postDelayed({isClickAllowed=true},1000L)
        }
        return current
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
    }
}