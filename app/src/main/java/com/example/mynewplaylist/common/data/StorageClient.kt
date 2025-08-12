package com.example.mynewplaylist.common.data

interface StorageClient<T> {
    fun storageData(data: T?)
    fun getData():T?
}