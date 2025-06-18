package com.example.mynewplaylist

import android.annotation.SuppressLint
import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import org.w3c.dom.Text
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
    private var mediaPlayer=MediaPlayer()
    //private var url:String=intent.extras?.getString("previewUrl").toString()
    //private var url = "https://audio-ssl.itunes.apple.com/itunes-assets/AudioPreview112/v4/ac/c7/d1/acc7d13f-6634-495f-caf6-491eccb505e8/mzaf_4002676889906514534.plus.aac.p.m4a"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.audio_player)
        backButton=findViewById<Button>(R.id.menu_button)
        backButton.setOnClickListener {
            val intent=Intent(this,SearchActivity::class.java)
            startActivity(intent)
        }
        play=findViewById(R.id.view2)
        trackName=findViewById<TextView>(R.id.track_name_for_audio)
        trackAuthor=findViewById<TextView>(R.id.track_artist)
        trackTime=findViewById<TextView>(R.id.this_duration)
        trackAlbum=findViewById<TextView>(R.id.this_album)
        trackYear=findViewById<TextView>(R.id.this_year)
        trackGenre=findViewById<TextView>(R.id.this_genre)
        trackCountry=findViewById<TextView>(R.id.this_country)
        timer=findViewById(R.id.timer)
        artwork=findViewById<ImageView>(R.id.cover)
        trackName.text=intent.extras?.getString("name")
        trackAuthor.text=intent.extras?.getString("name_artist")
        trackTime.text=intent.extras?.getString("duration")
        trackAlbum.text=intent.extras?.getString("album")
        trackGenre.text=intent.extras?.getString("genre")
        trackCountry.text=intent.extras?.getString("country")
        trackYear.text=intent.extras?.getString("year")?.substring(0,4)
        //val url=intent.extras?.getString("previewUrl").toString()
        //val url = "https://audio-ssl.itunes.apple.com/itunes-assets/AudioPreview112/v4/ac/c7/d1/acc7d13f-6634-495f-caf6-491eccb505e8/mzaf_4002676889906514534.plus.aac.p.m4a"

        val artworkurl=intent.extras?.getString("artwork")
        val newart=getCoverArtwork(artworkurl)
        Glide.with(applicationContext).load(newart).placeholder(R.drawable.vector3).transform(
            RoundedCorners(8)).into(artwork)
        val url=intent.extras?.getString("previewUrl").toString()
        preparePlayer(url)
        handler=Handler(Looper.getMainLooper())
        timer.text=SimpleDateFormat("m:ss", Locale.getDefault()).format(mediaPlayer.currentPosition)
        //var newValue=timer.text.toString()
        play.setOnClickListener{

            val newThread=Thread{
                handler?.postDelayed(object :Runnable{
                    override fun run() {
                        timer.text=SimpleDateFormat("m:ss", Locale.getDefault()).format(mediaPlayer.currentPosition)
                        handler?.postDelayed(this,1_000L)
                    }

                },1_000L
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
        mediaPlayer.setDataSource(url)
        mediaPlayer.prepareAsync()
        mediaPlayer.setOnPreparedListener{
            play.isEnabled=true
            playerState = STATE_PREPARED
        }
        mediaPlayer.setOnCompletionListener {
            playerState = STATE_PREPARED
        }
    }
    private fun startPlayer(){
        mediaPlayer.start()
        playerState= STATE_PLAYING
    }
    private fun pausePlayer(){
        mediaPlayer.pause()
        playerState= STATE_PAUSED
    }
    private fun playbackControl(){
        when(playerState){
            STATE_PLAYING->{
                play.setImageDrawable(getDrawable(R.drawable.pause))
                timer.text=SimpleDateFormat("m:ss", Locale.getDefault()).format(mediaPlayer.currentPosition)

                pausePlayer()
            }
            STATE_PAUSED, STATE_PREPARED, STATE_DEFAULT->{
                play.setImageDrawable(getDrawable(R.drawable.play))
                timer.text=SimpleDateFormat("m:ss", Locale.getDefault()).format(mediaPlayer.currentPosition)

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
        mediaPlayer.release()
    }

    override fun onStop() {
        super.onStop()

    }
}