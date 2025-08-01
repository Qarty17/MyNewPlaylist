package com.example.mynewplaylist.legacy.data.network

import com.example.mynewplaylist.legacy.data.dto.PlaylistResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PlaylistApi {
    @GET("/search?entity=song")
    fun search(@Query("term") text: String):Call<PlaylistResponse>
}