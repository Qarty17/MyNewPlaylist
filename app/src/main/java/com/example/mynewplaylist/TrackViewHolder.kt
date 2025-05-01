package com.example.mynewplaylist

import android.view.LayoutInflater
import android.view.RoundedCorner
import android.view.View
import android.view.ViewGroup
import android.view.ViewParent
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import java.text.SimpleDateFormat
import java.util.Locale


class TrackViewHolder(parent: ViewGroup): RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.track_view,parent,false)) {
    private val thisTrackName: TextView=itemView.findViewById<TextView>(R.id.track_name)
    private val thisArtistName: TextView=itemView.findViewById<TextView>(R.id.artist_name)
    private val thisTrackTime=itemView.findViewById<TextView>(R.id.track_time)
    private val thisArtworkUrl=itemView.findViewById<ImageView>(R.id.artwork_url)

    fun bind(model: Track){
        thisTrackName.text=model.trackName
        thisTrackTime.text=SimpleDateFormat("mm:ss", Locale.getDefault()).format(model.trackTimeMillis)
        thisArtistName.text=model.artistName
        Glide.with(itemView).load(model.artworkUrl100).placeholder(R.drawable.vector3).transform(
            RoundedCorners(2)).into(thisArtworkUrl)
    }
}