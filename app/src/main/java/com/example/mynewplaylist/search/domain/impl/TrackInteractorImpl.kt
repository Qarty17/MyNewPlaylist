package com.example.mynewplaylist.search.domain.impl

import com.example.mynewplaylist.common.Resource
import com.example.mynewplaylist.search.domain.api.TrackIntercator
import com.example.mynewplaylist.search.domain.api.TracksRepository
import com.example.mynewplaylist.search.domain.models.Track
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.concurrent.Executors

class TrackInteractorImpl(private val repository: TracksRepository): TrackIntercator {
    //private val executor= Executors.newCachedThreadPool()

    override fun searchTracks(expression: String): Flow<Pair<List<Track>?,String?>> {
        return repository.searchTracks(expression).map { result->
            when(result){
                is Resource.Success->{
                    Pair(result.data,null)
                }
                is Resource.Error->{
                    Pair(null,result.message)
                }
            }
        }
//        executor.execute{
//            when(val resource=repository.searchTracks(expression)){
//                is Resource.Success->consumer.consume(resource.data,null)
//                is Resource.Error->consumer.consume(null,resource.message)
//            }
//        }






    }
}