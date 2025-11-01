package com.example.mynewplaylist.search.data.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import com.example.mynewplaylist.search.data.NetworkClient
import com.example.mynewplaylist.search.data.dto.PlaylistRequest
import com.example.mynewplaylist.search.data.dto.Response
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.core.parameter.parametersOf
import org.koin.java.KoinJavaComponent.inject
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.jvm.Throws

class RetrofitNetworkClient(private val playListApi: PlaylistApi, private val context: Context): NetworkClient {
    override suspend fun doRequest(dto: Any): Response {
        if (isConnected()==false){
            return Response().apply { resultCode=-1 }
        }
        if (dto is PlaylistRequest){
            return withContext (Dispatchers.IO){
                try {
                    val response=playListApi.search(dto.expression)
                    response.apply { resultCode=200 }
                } catch (e: Throwable){
                    Response().apply { resultCode=500 }
                }

            }
//            val resp=playListApi.search(dto.expression).execute()
//            val body=resp.body()?: Response()
//            return body.apply { resultCode=resp.code() }
        }
        else{
            Log.d("Error","message")
            return Response().apply {
                resultCode=400


            }
        }
    }
    private fun isConnected():Boolean{
        val connectivityManager=context.getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        val capabilities=connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        if (capabilities!=null){
            when{
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)->return true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)->return true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)->return true
            }
        }
        return false
    }
}