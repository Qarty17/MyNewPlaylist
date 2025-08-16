package com.example.mynewplaylist.search.domain.impl

import com.example.mynewplaylist.creator.Resource
import com.example.mynewplaylist.search.domain.api.TrackIntercator
import com.example.mynewplaylist.search.domain.api.TracksRepository
import java.util.concurrent.Executors

class TrackInteractorImpl(private val repository: TracksRepository): TrackIntercator {
    private val executor= Executors.newCachedThreadPool()

    override fun searchTracks(expression: String, consumer: TrackIntercator.TrackConsumer) {

        executor.execute{
            when(val resource=repository.searchTracks(expression)){
                is Resource.Success->consumer.consume(resource.data,null)
                is Resource.Error->consumer.consume(null,resource.message)
            }
        }






    }
}