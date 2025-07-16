package com.example.mynewplaylist.domain.impl

import com.example.mynewplaylist.domain.api.TrackIntercator
import com.example.mynewplaylist.domain.api.TracksRepository
import java.util.concurrent.Executors

class TrackInteractorImpl(private val repository: TracksRepository):TrackIntercator {
    private val executor=Executors.newCachedThreadPool()

    override fun searchTracks(expression: String, consumer: TrackIntercator.TrackConsumer) {
        
        executor.execute{
            consumer.consume(repository.searchTracks(expression))
        }






    }
}