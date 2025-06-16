package com.example.mynewplaylist

import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners

class AudioplayerActivity() : AppCompatActivity() {
    lateinit var backButton: Button
    lateinit var trackName: TextView
    lateinit var trackAuthor: TextView
    lateinit var trackTime: TextView
    lateinit var trackAlbum: TextView
    lateinit var trackYear: TextView
    lateinit var trackGenre: TextView
    lateinit var trackCountry: TextView
    lateinit var artwork: ImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.audio_player)
        backButton=findViewById<Button>(R.id.menu_button)
        backButton.setOnClickListener {
            finish()
        }

        trackName=findViewById<TextView>(R.id.track_name_for_audio)
        trackAuthor=findViewById<TextView>(R.id.track_artist)
        trackTime=findViewById<TextView>(R.id.this_duration)
        trackAlbum=findViewById<TextView>(R.id.this_album)
        trackYear=findViewById<TextView>(R.id.this_year)
        trackGenre=findViewById<TextView>(R.id.this_genre)
        trackCountry=findViewById<TextView>(R.id.this_country)
        artwork=findViewById<ImageView>(R.id.cover)
        trackName.text=intent.extras?.getString("name")
        trackAuthor.text=intent.extras?.getString("name_artist")
        trackTime.text=intent.extras?.getString("duration")
        trackAlbum.text=intent.extras?.getString("album")

        trackGenre.text=intent.extras?.getString("genre")
        trackCountry.text=intent.extras?.getString("country")

        trackYear.text=intent.extras?.getString("year")?.substring(0,4)
        val artworkurl=intent.extras?.getString("artwork")
        val newart=getCoverArtwork(artworkurl)
        Glide.with(applicationContext).load(newart).placeholder(R.drawable.vector3).transform(
            RoundedCorners(8)).into(artwork)



    }

    fun getCoverArtwork(url: String?): String{
        return url?.replaceAfterLast('/',"512x512bb.jpg").toString()
    }

}