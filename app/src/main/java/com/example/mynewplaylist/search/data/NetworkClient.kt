package com.example.mynewplaylist.search.data

import com.example.mynewplaylist.search.data.dto.Response

interface NetworkClient {
    fun doRequest(dto:Any): Response
}