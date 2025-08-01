package com.example.mynewplaylist.player.ui

import android.R.attr.text
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
import com.example.mynewplaylist.R
import com.example.mynewplaylist.creator.Creator
import com.example.mynewplaylist.databinding.AudioPlayerBinding
import java.text.SimpleDateFormat
import java.util.Locale

class AudioplayerActivity : AppCompatActivity() {
    private lateinit var binding: AudioPlayerBinding
    private var handler: Handler?=null
    private var mediaPlayer= Creator.provideMediaPlayerInteractor()
    private lateinit var newThread:Thread
    private var stop=false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= AudioPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.menuButton.setOnClickListener {
            finish()
        }
        binding.trackNameForAudio.text=intent.extras?.getString("name")
        binding.trackArtist.text=intent.extras?.getString("name_artist")
        binding.thisDuration.text=intent.extras?.getString("duration")
        binding.thisAlbum.text=intent.extras?.getString("album")
        binding.thisGenre.text=intent.extras?.getString("genre")
        binding.thisCountry.text=intent.extras?.getString("country")
        binding.thisYear.text=intent.extras?.getString("year")?.substring(0,4)
        val artworkurl=intent.extras?.getString("artwork")
        val newart=getCoverArtwork(artworkurl)
        Glide.with(applicationContext).load(newart).placeholder(R.drawable.vector3).transform(
            RoundedCorners(8)
        ).into(binding.cover)
        val url=intent.extras?.getString("previewUrl").toString()
        preparePlayer(url)
        handler= Handler(Looper.getMainLooper())
        binding.timer.text= SimpleDateFormat("m:ss", Locale.getDefault()).format(mediaPlayer.getCurrentPosition())
        binding.view2.setOnClickListener{
            newThread=Thread{
                handler?.postDelayed(object :Runnable{
                    override fun run() {
                        if(!stop) {
                            binding.timer.text = SimpleDateFormat(
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
        mediaPlayer.preparePlayer(binding.view2,url, onPrepared = {
            binding.view2.isEnabled=true
            playerState= STATE_PREPARED
        }){
            binding.view2.setImageDrawable(getDrawable(R.drawable.pause))
            playerState= STATE_PREPARED
        }
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
                binding.view2.setImageDrawable(getDrawable(R.drawable.pause))
                binding.timer.text= SimpleDateFormat("m:ss", Locale.getDefault()).format(mediaPlayer.getCurrentPosition())
                pausePlayer()
            }
            STATE_PAUSED,STATE_PREPARED, STATE_DEFAULT ->{
                binding.view2.setImageDrawable(getDrawable(R.drawable.play))
                binding.timer.text= SimpleDateFormat("m:ss", Locale.getDefault()).format(mediaPlayer.getCurrentPosition())
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