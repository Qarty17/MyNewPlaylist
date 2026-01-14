package com.example.mynewplaylist.search.data

import com.example.mynewplaylist.common.data.StorageClient
import com.example.mynewplaylist.search.domain.api.HistoryRepository
import com.example.mynewplaylist.search.domain.models.Track


class HistoryRepositoryImpl(private val storage: StorageClient<ArrayList<Track>>): HistoryRepository {
    override fun getHistory(): ArrayList<Track> {
        val tracks=storage.getData()?:arrayListOf()
        return tracks
    }
    override fun saveHistory(tracks: ArrayList<Track>) {
        storage.storageData(tracks)
    }
    override fun clearHistory() {
        storage.removeData()
    }



}