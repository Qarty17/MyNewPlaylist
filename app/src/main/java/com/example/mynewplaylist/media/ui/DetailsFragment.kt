package com.example.mynewplaylist.media.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.mynewplaylist.R
import com.example.mynewplaylist.databinding.FragmentMediaBinding
import com.example.mynewplaylist.media.presentation.MedialibraryAdapter
import com.google.android.material.tabs.TabLayoutMediator


class DetailsFragment: Fragment() {
    private lateinit var binding: FragmentMediaBinding
    private lateinit var tabMediator: TabLayoutMediator
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentMediaBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewPager.adapter= MedialibraryAdapter(childFragmentManager,lifecycle,getString(R.string.not_favorite_tracks),getString(R.string.not_playlists))
        tabMediator= TabLayoutMediator(binding.tabLayout,binding.viewPager){tab,position->
            when(position) {
                0 -> tab.text = getString(R.string.favorite_tracks)
                1 -> tab.text = getString(R.string.playlists)
            }
        }
        tabMediator.attach()
    }
}