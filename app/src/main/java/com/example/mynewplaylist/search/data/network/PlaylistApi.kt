package com.example.mynewplaylist.search.data.network

import com.example.mynewplaylist.search.data.dto.PlaylistResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PlaylistApi {
    @GET("/search?entity=song")
    fun search(@Query("term") text: String): Call<PlaylistResponse>
}