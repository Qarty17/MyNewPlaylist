package com.example.mynewplaylist.data.dto

import com.example.mynewplaylist.domain.models.Track


class PlaylistResponse(
    val results:List<TrackDto>
):Response()