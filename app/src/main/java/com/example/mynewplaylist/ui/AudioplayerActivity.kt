package com.example.mynewplaylist.ui


import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.mynewplaylist.Creator
import com.example.mynewplaylist.R
import java.text.SimpleDateFormat
import java.util.Locale

class AudioplayerActivity : AppCompatActivity() {

    private var handler: Handler?=null
    private lateinit var backButton: Button
    private lateinit var trackName: TextView
    private lateinit var trackAuthor: TextView
    private lateinit var trackTime: TextView
    private lateinit var trackAlbum: TextView
    private lateinit var trackYear: TextView
    private lateinit var trackGenre: TextView
    private lateinit var trackCountry: TextView
    private lateinit var artwork: ImageView
    private lateinit var play:ImageButton
    private lateinit var timer:TextView
    private var mediaPlayer=Creator.provideMediaPlayerInteractor()
    private lateinit var newThread:Thread
    private var stop=false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.audio_player)
        backButton=findViewById(R.id.menu_button)
        backButton.setOnClickListener {
            finish()
        }
        play=findViewById(R.id.view2)
        trackName=findViewById(R.id.track_name_for_audio)
        trackAuthor=findViewById(R.id.track_artist)
        trackTime=findViewById(R.id.this_duration)
        trackAlbum=findViewById(R.id.this_album)
        trackYear=findViewById(R.id.this_year)
        trackGenre=findViewById(R.id.this_genre)
        trackCountry=findViewById(R.id.this_country)
        timer=findViewById(R.id.timer)
        artwork=findViewById(R.id.cover)
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
        val url=intent.extras?.getString("previewUrl").toString()
        preparePlayer(url)
        handler=Handler(Looper.getMainLooper())
        timer.text=SimpleDateFormat("m:ss", Locale.getDefault()).format(mediaPlayer.getCurrentPosition())
        play.setOnClickListener{
            newThread=Thread{
                handler?.postDelayed(object :Runnable{
                    override fun run() {
                        if(!stop) {
                            timer.text = SimpleDateFormat(
                                "m:ss",
                                Locale.getDefault()
                            ).format(mediaPlayer.getCurrentPosition())
                            handler?.postDelayed(this, 300L)
                        }


                    }

                },300L
                )
            }

            newThread.start()

            playbackControl()
        }


    }

    private fun getCoverArtwork(url: String?): String{
        return url?.replaceAfterLast('/',"512x512bb.jpg").toString()
    }
    companion object{
        private const val STATE_DEFAULT=0
        private const val STATE_PREPARED=1
        private const val STATE_PLAYING=2
        private const val STATE_PAUSED=3
    }
    private var playerState= STATE_DEFAULT
    private fun preparePlayer(url:String){
        mediaPlayer.preparePlayer(play,url)
    }
    private fun startPlayer(){
        mediaPlayer.startPlayer()
        playerState= STATE_PLAYING
    }
    private fun pausePlayer(){
        mediaPlayer.pausePlayer()
        playerState= STATE_PAUSED
    }
    private fun playbackControl(){
        when(playerState){
            STATE_PLAYING ->{
                play.setImageDrawable(getDrawable(R.drawable.pause))
                timer.text=SimpleDateFormat("m:ss", Locale.getDefault()).format(mediaPlayer.getCurrentPosition())
                pausePlayer()
            }
            STATE_PAUSED,STATE_PREPARED, STATE_DEFAULT ->{
                play.setImageDrawable(getDrawable(R.drawable.play))
                timer.text=SimpleDateFormat("m:ss", Locale.getDefault()).format(mediaPlayer.getCurrentPosition())
                startPlayer()
            }
        }
    }

    override fun onPause() {
        super.onPause()
        pausePlayer()
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.releasePlayer()
        stop=true


    }


}