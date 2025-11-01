package com.example.mynewplaylist.media.data.converters

import com.example.mynewplaylist.media.data.db.entity.TrackEntity

import com.example.mynewplaylist.search.domain.models.Track


class TrackDbConvertor {
    fun map(track: Track): TrackEntity{
        return TrackEntity(track.trackName,
            track.artistName,
            track.trackTimeMillis,
            track.artworkUrl100,
            track.trackId,
            track.collectionName,
            track.releaseDate,
            track.primaryGenreName,
            track.country,
            track.previewUrl)
    }
    fun map(track: TrackEntity): Track{
        return Track(track.trackName,
            track.artistName,
            track.trackTimeMillis,
            track.artworkUrl100,
            track.trackId,
            track.collectionName,
            track.releaseDate,
            track.primaryGenreName,
            track.country,
            track.previewUrl)
    }
}