package com.example.mynewplaylist.player.ui

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.mynewplaylist.R
import com.example.mynewplaylist.creator.Creator
import com.example.mynewplaylist.databinding.AudioPlayerBinding
import java.text.SimpleDateFormat
import java.util.Locale

class AudioplayerActivity : AppCompatActivity() {
    private lateinit var binding: AudioPlayerBinding
    private lateinit var viewModel: PlayerViewModel
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

        viewModel= ViewModelProvider(this,PlayerViewModel.getFactory(url))[PlayerViewModel::class.java]
        viewModel.observePlayerState().observe(this) {
            changeButton(it == PlayerViewModel.STATE_PLAYING)
            enableButton(it!= PlayerViewModel.STATE_DEFAULT)
        }
        viewModel.observeProgressTime().observe(this) {
            binding.timer.text=it
        }
        binding.view2.setOnClickListener{
            viewModel.onPlayButtonClicked()
        }
    }
    private fun getCoverArtwork(url: String?): String{
        return url?.replaceAfterLast('/',"512x512bb.jpg").toString()
    }
    private fun enableButton(isEnabled: Boolean){
        binding.view2.isEnabled=isEnabled
    }
    private fun changeButton(isPlaying: Boolean) {
        if(isPlaying){
            binding.view2.setImageDrawable(getDrawable(R.drawable.play))
        }else{
            binding.view2.setImageDrawable(getDrawable(R.drawable.pause))
        }
    }
    override fun onPause() {
        super.onPause()
        viewModel.onPause()
    }
}