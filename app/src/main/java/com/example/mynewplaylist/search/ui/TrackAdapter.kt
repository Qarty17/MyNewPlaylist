package com.example.mynewplaylist.search.ui

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mynewplaylist.search.ui.TrackViewHolder
import com.example.mynewplaylist.search.domain.models.Track

class TrackAdapter(val listener: Listener, private val onTrackClick: (Track) -> Unit) : RecyclerView.Adapter<TrackViewHolder>() {

    var tracks= ArrayList<Track>()
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TrackViewHolder = TrackViewHolder.Companion.from(parent)

    override fun onBindViewHolder(
        holder: TrackViewHolder,
        position: Int
    ) {
        val track=tracks[position]
        holder.bind(tracks[position],listener)
        holder.itemView.setOnClickListener {
            onTrackClick.invoke(track)
            listener.onClick(track)
            listener.addName(track)

        }

    }

    override fun getItemCount(): Int {
        return tracks.size
    }
    interface Listener{
        fun onClick(track: Track)
        fun addName(track: Track): String
    }

}