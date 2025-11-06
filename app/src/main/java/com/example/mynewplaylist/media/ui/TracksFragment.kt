package com.example.mynewplaylist.media.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.mynewplaylist.databinding.TrackFragmentBinding
import com.example.mynewplaylist.media.presentation.NewPlaylistViewModel
import com.example.mynewplaylist.search.domain.models.Track
import com.example.mynewplaylist.search.presentation.TrackAdapter
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class TracksFragment: Fragment(), TrackAdapter.Listener {
    private lateinit var favoriteTrackAdapter: TrackAdapter
    private val viewModel: NewPlaylistViewModel by viewModel()
    companion object{
        private const val TEXT="text"
        fun newInstance(text: String)= TracksFragment().apply {
            arguments= Bundle().apply {
                putString(TEXT,text)
            }
        }
    }
    private lateinit var binding: TrackFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= TrackFragmentBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.text.text=requireArguments().getString(TEXT).toString()
        favoriteTrackAdapter= TrackAdapter(this){
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.getFavoriteTracks().collect { tracks-> favoriteTrackAdapter.tracks=tracks }
        }
        binding.recyclerViewMediaHistory.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.recyclerViewMediaHistory.adapter=favoriteTrackAdapter
        favoriteTrackAdapter.notifyDataSetChanged()
        binding.recyclerViewMediaHistory.visibility=View.VISIBLE

    }

    override fun onClick(track: Track) {
        TODO("Not yet implemented")
    }

    override fun addName(track: Track): String {
        TODO("Not yet implemented")
    }
}