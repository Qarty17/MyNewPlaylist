package com.example.mynewplaylist.media.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mynewplaylist.R

import com.example.mynewplaylist.databinding.TrackFragmentBinding
import com.example.mynewplaylist.media.presentation.FavoriteState
import com.example.mynewplaylist.media.presentation.MediaViewModel
import com.example.mynewplaylist.player.ui.AudioplayerFragment
import com.example.mynewplaylist.search.domain.models.Track
import com.example.mynewplaylist.search.presentation.TrackAdapter
import com.example.mynewplaylist.search.ui.PlaylistState
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.Locale
//добрый день, не вижу пометки обязательно на гит хабе
class TracksFragment: Fragment(), TrackAdapter.Listener {
    private var favoriteTrackAdapter: TrackAdapter= TrackAdapter(this){

    }
    private val viewModel: MediaViewModel by viewModel()

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

        viewModel.observeFavorite().observe(viewLifecycleOwner){
            render(it.state)
            Log.d("RENDERCreated",it.state.toString())

        }



        binding.recyclerViewMediaHistory.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.recyclerViewMediaHistory.adapter=favoriteTrackAdapter
        favoriteTrackAdapter.notifyDataSetChanged()
        binding.recyclerViewMediaHistory.visibility=View.VISIBLE
        binding.imageNotFound


    }
    fun showContent(tracks:ArrayList<Track>){
        favoriteTrackAdapter.tracks=tracks
        favoriteTrackAdapter.notifyDataSetChanged()
        binding.recyclerViewMediaHistory.visibility= View.VISIBLE
        binding.text.visibility=View.GONE
        binding.imageNotFound.visibility= View.GONE
    }
    fun showEmpty(){

        binding.imageNotFound.visibility= View.VISIBLE
        binding.text.visibility=View.VISIBLE
        binding.recyclerViewMediaHistory.visibility= View.GONE
    }
    fun render(state: PlaylistState){
        when(state){
            is PlaylistState.Content -> showContent(state.tracks)
            else -> showEmpty()
        }
        Log.d("rendertracks",favoriteTrackAdapter.tracks.toString())
    }

    override fun onResume() {
        super.onResume()
        viewModel.getTracks()
        favoriteTrackAdapter.notifyDataSetChanged()


    }
    override fun onClick(track: Track) {
        findNavController().navigate(R.id.action_detailsFragment_to_audioplayerFragment,
            AudioplayerFragment.createArgs(
                track.trackName,
                track.artistName,
                //track.trackTimeMillis,
                SimpleDateFormat("mm:ss", Locale.getDefault()).format(track.trackTimeMillis),
                track.collectionName,
                track.primaryGenreName,
                track.country,
                track.releaseDate,
                track.artworkUrl100,
                track.previewUrl,
                false,
                track.trackId
            ))
        Log.d("DURATION",track.trackTimeMillis.toString())
    }

    override fun addName(track: Track): String {
        return track.trackName
    }
}