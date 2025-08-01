package com.example.mynewplaylist.legacy.data

import com.example.mynewplaylist.legacy.data.dto.Response


interface NetworkClient {
    fun doRequest(dto:Any):Response
}