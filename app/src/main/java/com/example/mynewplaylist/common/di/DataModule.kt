package com.example.mynewplaylist.common.di

import android.content.Context
import android.content.SharedPreferences
import android.media.MediaPlayer
import androidx.room.Room
import com.example.mynewplaylist.common.data.StorageClient
import com.example.mynewplaylist.common.data.storage.PrefsStorageClient
import com.example.mynewplaylist.media.data.db.AppDataBase
import com.example.mynewplaylist.search.data.HistoryRepositoryImpl
import com.example.mynewplaylist.search.data.NetworkClient
import com.example.mynewplaylist.search.data.network.PlaylistApi
import com.example.mynewplaylist.search.data.network.RetrofitNetworkClient
import com.example.mynewplaylist.search.domain.api.HistoryRepository
import com.example.mynewplaylist.search.domain.models.Track
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val dataModule= module {
    single {
        Room.databaseBuilder(androidContext(), AppDataBase::class.java,"database.db").build()
    }
    single<PlaylistApi> {
        Retrofit.Builder()
            .baseUrl("https://itunes.apple.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PlaylistApi::class.java)
    }
    single<SharedPreferences>{
        androidContext()
            .getSharedPreferences("PLAYLIST_SEARCH", Context.MODE_PRIVATE)
    }
    factory { Gson() }
    factory<MediaPlayer>{
        MediaPlayer()
    }
    single<HistoryRepository> {
        HistoryRepositoryImpl(get())
    }
    single <StorageClient<ArrayList<Track>>>{
        PrefsStorageClient<ArrayList<Track>>(get(),get(),dataKey="history",type=object : TypeToken<ArrayList<Track>>(){}.type)
    }
    single <NetworkClient>{
        RetrofitNetworkClient(get(),androidContext())
    }


}