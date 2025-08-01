package com.example.mynewplaylist.legacy.data.network

import com.example.mynewplaylist.legacy.data.NetworkClient
import com.example.mynewplaylist.legacy.data.dto.PlaylistRequest
import com.example.mynewplaylist.legacy.data.dto.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitNetworkClient:NetworkClient {
    private val playlistBaseUrl = "https://itunes.apple.com"
    private val retrofit=Retrofit.Builder()
        .baseUrl(playlistBaseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private var playlistService = retrofit.create(PlaylistApi::class.java)
    override fun doRequest(dto: Any): Response {
        if (dto is PlaylistRequest){

            val resp=playlistService.search(dto.expression).execute()
            val body=resp.body()?:Response()
            return body.apply { resultCode=resp.code() }
        }
        else{
            return Response().apply { resultCode=400 }
        }
    }
}