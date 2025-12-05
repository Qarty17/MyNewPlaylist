package com.example.mynewplaylist.media.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mynewplaylist.media.domain.db.HistoryMediaInteractor
import com.example.mynewplaylist.media.domain.models.TrackData
import com.example.mynewplaylist.search.domain.models.Track
import com.example.mynewplaylist.search.ui.PlaylistState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class MediaViewModel(private val historyMediaInteractor: HistoryMediaInteractor): ViewModel() {
    private val favoriteLiveData= MutableLiveData<FavoriteState>(FavoriteState(PlaylistState.Empty("")))
    private var tracks=arrayListOf<Track>()
    fun observeFavorite(): LiveData<FavoriteState> = favoriteLiveData
    init {
        stateControl()
        viewModelScope.launch {
            val convertFlow:Flow<ArrayList<Track>> = historyMediaInteractor.getFavoriteTracks().map { trackDataList->
                trackDataList.map { trackDataItem->
                    Track(trackDataItem.trackName,
                        trackDataItem.artistName,
                        trackDataItem.trackTimeMillis,
                        trackDataItem.artworkUrl100,
                        trackDataItem.trackId,
                        trackDataItem.collectionName,
                        trackDataItem.releaseDate,
                        trackDataItem.primaryGenreName,
                        trackDataItem.country,
                        trackDataItem.previewUrl
                    )
                } as ArrayList<Track>
            }
            convertFlow.collect { value ->
                run {
                    tracks.addAll(value)
                    if(value.isEmpty()){
                        PlaylistState.Empty("")
                        favoriteLiveData.value= FavoriteState(PlaylistState.Empty(""))
                    }else{
                        PlaylistState.Content(value)
                        favoriteLiveData.value = FavoriteState(PlaylistState.Content(value))
                    }
                    //favoriteLiveData.value = FavoriteState(PlaylistState.Content(value))
                }
            }

        }
        Log.d("TracksIsEmpty3",favoriteLiveData.value.toString())

    }
    fun getTracks(){
        viewModelScope.launch {
            val convertFlow:Flow<ArrayList<Track>> = historyMediaInteractor.getFavoriteTracks().map { trackDataList->
                trackDataList.map { trackDataItem->
                    Track(trackDataItem.trackName,
                        trackDataItem.artistName,
                        trackDataItem.trackTimeMillis,
                        trackDataItem.artworkUrl100,
                        trackDataItem.trackId,
                        trackDataItem.collectionName,
                        trackDataItem.releaseDate,
                        trackDataItem.primaryGenreName,
                        trackDataItem.country,
                        trackDataItem.previewUrl
                    )
                } as ArrayList<Track>
            }
            convertFlow.collect { value ->
                run {
                    if(value.isEmpty()){
                        PlaylistState.Empty("")
                        favoriteLiveData.value= FavoriteState(PlaylistState.Empty(""))
                    }else{
                        PlaylistState.Content(value)
                        favoriteLiveData.value = FavoriteState(PlaylistState.Content(value))
                    }

                }
            }
        }
    }
    fun renderState(state: PlaylistState){
        favoriteLiveData.postValue(FavoriteState(state))
    }
    private fun stateControl(){
        Log.d("TracksIsEmpty",tracks.toString())
        when{
            tracks.isEmpty()->renderState(PlaylistState.Empty(""))
            else->renderState(PlaylistState.Content(tracks))
        }
        Log.d("TracksIsEmpty2",favoriteLiveData.value.toString())
    }

}