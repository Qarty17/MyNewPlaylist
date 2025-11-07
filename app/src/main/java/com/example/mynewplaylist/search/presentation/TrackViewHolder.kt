package com.example.mynewplaylist.search.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.mynewplaylist.R
import com.example.mynewplaylist.databinding.TrackViewBinding
import com.example.mynewplaylist.search.domain.models.Track
import java.text.SimpleDateFormat
import java.util.Locale

class TrackViewHolder(private val binding: TrackViewBinding): RecyclerView.ViewHolder(binding.root){
    companion object{
        fun from(parent: ViewGroup): TrackViewHolder{
            val inflater= LayoutInflater.from(parent.context)
            val binding= TrackViewBinding.inflate(inflater,parent,false)
            return TrackViewHolder(binding)
        }
    }
    fun bind(model: Track, listener: TrackAdapter.Listener){
        binding.trackName.text=model.trackName
        binding.trackTime.text= SimpleDateFormat("mm:ss", Locale.getDefault()).format(model.trackTimeMillis)
        binding.artistName.text=model.artistName
        Glide.with(binding.root).load(model.artworkUrl100).placeholder(R.drawable.vector3).transform(
            RoundedCorners(2)
        ).into(binding.artworkUrl)


    }
}