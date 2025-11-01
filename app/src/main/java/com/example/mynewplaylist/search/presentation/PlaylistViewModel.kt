package com.example.mynewplaylist.search.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mynewplaylist.search.domain.api.HistoryInteractor
import com.example.mynewplaylist.search.domain.api.TrackIntercator
import com.example.mynewplaylist.search.domain.models.Track
import com.example.mynewplaylist.search.ui.PlaylistState
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class PlaylistViewModel(private val trackIntercator: TrackIntercator,private val historyIntercator: HistoryInteractor): ViewModel() {
    private var latestSearchText: String? = null
    private val searchLiveData= MutableLiveData(SearchState(historyIntercator.getHistory(),PlaylistState.Content(arrayListOf())))
    fun observeSearch(): LiveData<SearchState> = searchLiveData
    private var searchJob: Job? = null

    private fun searchCreate(newSearchText:String){
        if (newSearchText.isNotEmpty()){
            renderState(PlaylistState.Loading)

            viewModelScope.launch {
                trackIntercator.searchTracks(newSearchText)
                    .collect { pair-> processResult(pair.first,pair.second)}
            }
        }
    }
    private fun processResult(foundTracks:List<Track>?,errorMessage:String?){
        val tracks=arrayListOf<Track>()
        if(foundTracks?.isNotEmpty()==true){
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
    fun getHistory(): ArrayList<Track>{
        return historyIntercator.getHistory()
    }
    fun clearHistory(){
        historyIntercator.clearHistory()
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
        historyIntercator.saveHistory(searchLiveData.value?.history!!)
    }
    fun clearText(){
        searchLiveData.postValue(SearchState(searchLiveData.value?.history ?: arrayListOf(), PlaylistState.Content(arrayListOf())))
    }
    fun searchDebounce(changedText: String){
        if(latestSearchText==changedText)
        {
            return
        }
        latestSearchText=changedText
        searchJob?.cancel()
        searchJob=viewModelScope.launch {
            delay(2000L)
            searchCreate(changedText)

        }
    }

    private fun renderState(state: PlaylistState) {
        searchLiveData.postValue(SearchState(searchLiveData.value?.history ?: arrayListOf(),state))


    }

    fun clearHandler(){
        searchJob?.cancel()

        latestSearchText=null
    }

}