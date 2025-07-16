package com.example.mynewplaylist.data

import com.example.mynewplaylist.data.dto.Response


interface NetworkClient {
    fun doRequest(dto:Any):Response
}