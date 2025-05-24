package com.example.mynewplaylist


import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class TrackAdapter(private val onTrackClick:(Track)->Unit) : RecyclerView.Adapter<TrackViewHolder>() {

    var tracks= ArrayList<Track>()
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TrackViewHolder= TrackViewHolder(parent)

    override fun onBindViewHolder(
        holder: TrackViewHolder,
        position: Int
    ) {
        val track=tracks[position]
        holder.bind(tracks[position])
        holder.itemView.setOnClickListener {
            onTrackClick.invoke(track)
        }
    }

    override fun getItemCount(): Int {
        return tracks.size
    }

}
