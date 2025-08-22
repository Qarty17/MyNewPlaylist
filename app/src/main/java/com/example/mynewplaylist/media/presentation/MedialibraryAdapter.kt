package com.example.mynewplaylist.media.presentation

import android.R.attr.text
import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.mynewplaylist.media.ui.PlaylistFragment
import com.example.mynewplaylist.media.ui.TracksFragment

class MedialibraryAdapter(fragmentManager: FragmentManager,lifecycle: Lifecycle,private val text: String,private val text2: String): FragmentStateAdapter(fragmentManager,lifecycle) {
    override fun createFragment(position: Int): Fragment {
        return when(position){
            0-> TracksFragment.newInstance(text)
            else -> PlaylistFragment.newInstance(text2)

        }
    }

    override fun getItemCount(): Int {
        return 2
    }
}