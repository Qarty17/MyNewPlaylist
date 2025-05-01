package com.example.mynewplaylist

import java.text.SimpleDateFormat
import java.util.Locale


data class Track (
    val trackName: String,
    val artistName: String,
    val trackTimeMillis: Int,
    val artworkUrl100: String
){
    init {

    }
}