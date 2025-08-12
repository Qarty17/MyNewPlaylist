package com.example.mynewplaylist.common.data.storage

import android.content.Context
import android.content.SharedPreferences
import com.example.mynewplaylist.common.data.StorageClient
import com.google.gson.Gson
import java.lang.reflect.Type
import androidx.core.content.edit


class PrefsStorageClient<T>(
    private val context: Context,
    private val dataKey:String,
    private val type: Type): StorageClient<T>
{
    private val prefs: SharedPreferences=context.getSharedPreferences("PLAYLIST_SEARCH", Context.MODE_PRIVATE)
    private val gson= Gson()
    override fun storageData(data: T?) {
        prefs.edit { putString(dataKey, gson.toJson(data, type)) }
    }

    override fun getData(): T? {
        val dataJson=prefs.getString(dataKey,null)
        return if(dataJson==null){
            null
        }else{
            gson.fromJson(dataJson,type)
        }
    }
}