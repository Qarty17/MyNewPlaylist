package com.example.mynewplaylist.media.ui

import android.os.Bundle

import androidx.appcompat.app.AppCompatActivity

import com.example.mynewplaylist.R
import com.example.mynewplaylist.databinding.ActivityMediaBinding
import com.example.mynewplaylist.media.presentation.MedialibraryAdapter
import com.google.android.material.tabs.TabLayoutMediator

class MediaActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMediaBinding
    private lateinit var tabMediator: TabLayoutMediator
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMediaBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.back.setOnClickListener {
            finish()
        }
        binding.viewPager.adapter= MedialibraryAdapter(supportFragmentManager,lifecycle,getString(R.string.not_favorite_tracks),getString(R.string.not_playlists))
        tabMediator= TabLayoutMediator(binding.tabLayout,binding.viewPager){tab,position->
            when(position) {
                0 -> tab.text = getString(R.string.favorite_tracks)
                1 -> tab.text = getString(R.string.playlists)
            }
        }
        tabMediator.attach()
    }

    override fun onDestroy() {
        super.onDestroy()
        tabMediator.detach()
    }
}