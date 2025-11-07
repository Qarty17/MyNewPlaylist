package com.example.mynewplaylist.common.data.storage

import android.content.SharedPreferences
import com.example.mynewplaylist.common.data.StorageClient
import com.google.gson.Gson
import java.lang.reflect.Type
import androidx.core.content.edit



class PrefsStorageClient<T>(
    private val prefs: SharedPreferences,
    private val gson:Gson,
    private val dataKey:String,
    private val type: Type): StorageClient<T>
{

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

    override fun removeData() {
        prefs.edit { remove(dataKey) }
    }
}