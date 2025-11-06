package com.example.mynewplaylist.player.ui

import android.R.attr.track
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.mynewplaylist.R
import com.example.mynewplaylist.databinding.FragmentAudioPlayerBinding
import com.example.mynewplaylist.player.presentation.FavoriteState

import com.example.mynewplaylist.player.presentation.PlayerViewModel
import com.example.mynewplaylist.search.domain.models.Track

import kotlinx.coroutines.Job
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import java.text.SimpleDateFormat
import java.util.Locale
import kotlin.toString

class AudioplayerFragment: Fragment() {
    private lateinit var url: String
    private lateinit var isFavorite: String
    private lateinit var track: Track
    private lateinit var binding: FragmentAudioPlayerBinding
    private val viewModel: PlayerViewModel by viewModel<PlayerViewModel>{
        parametersOf(url,track)
    }
    companion object{
        private const val NAME="name"
        private const val ARTIST="artist"
        private const val DURATION="duration"
        private const val ALBUM="album"
        private const val GENRE="genre"
        private const val COUNTRY="country"
        private const val YEAR="year"
        private const val ARTWORKURL="artworkurl"
        private const val PREVIEWURL="previewurl"
        private const val ISFAVORITE="isFavorite"
        private const val ID="id"
        fun createArgs(nameTrack: String,
                       artistTrack: String,
                       duration: Int,
                       album: String,
                       genre: String,
                       country: String,
                       year: String,
                       artworkUrl:String,
                       previewUrl:String,
                       isFavorite: Boolean,
                       id: String

        ): Bundle=
            bundleOf(NAME to nameTrack,
                ARTIST to artistTrack,
                DURATION to duration,
                ALBUM to album,
                GENRE to genre,
                COUNTRY to country,
                YEAR to year,
                ARTWORKURL to artworkUrl,
                PREVIEWURL to previewUrl,
                ISFAVORITE to isFavorite,
                ID to id)
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentAudioPlayerBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.menuButton.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.trackNameForAudio.text=requireArguments().getString(NAME)
        binding.trackArtist.text=requireArguments().getString(ARTIST)
        binding.thisDuration.text=requireArguments().getString(DURATION)
        binding.thisAlbum.text=requireArguments().getString(ALBUM)
        binding.thisGenre.text=requireArguments().getString(GENRE)
        binding.thisCountry.text=requireArguments().getString(COUNTRY)
        binding.thisYear.text= requireArguments().getString(YEAR)?.substring(0,4)
        val artworkurl=requireArguments().getString(ARTWORKURL)
        val newart=getCoverArtwork(artworkurl)
        Glide.with(requireContext()).load(newart).placeholder(R.drawable.vector3).transform(
            RoundedCorners(8)
        ).into(binding.cover)
        url= requireArguments().getString(PREVIEWURL).toString()
        isFavorite=requireArguments().getString(ISFAVORITE).toString()
        track= Track(
            requireArguments().getString(NAME).toString(),
            requireArguments().getString(ARTIST).toString(),
            requireArguments().getString(DURATION)?.toInt() ?: 0,
            requireArguments().getString(ARTWORKURL).toString(),
            requireArguments().getString(ID).toString(),
            requireArguments().getString(ALBUM).toString(),
            requireArguments().getString(YEAR).toString(),
            requireArguments().getString(GENRE).toString(),
            requireArguments().getString(COUNTRY).toString(),
            requireArguments().getString(PREVIEWURL).toString(),
            requireArguments().getString(ISFAVORITE).toBoolean()

        )
        binding.view2.setOnClickListener{
            viewModel.onPlayButtonClicked()
        }
        binding.view3.setOnClickListener {
            if(requireArguments().getString(ISFAVORITE).toBoolean()==false){
                track.isFavorite=true
                viewModel.onFavoriteClicked()
            }else
            {
                track.isFavorite=false
                viewModel.onFavoriteClicked()
            }

        }
        viewModel.observePlayerState().observe(viewLifecycleOwner){
            binding.view2.isEnabled=it.isPlayButtonPlaying
            changeButton(it.isPlayButtonPlaying)
            enableButton(it.isPlayButtonEnabled)
            binding.timer.text=it.progress
        }
        viewModel.observeFavoriteState().observe(viewLifecycleOwner) {
            enableButtonFavorite(true)
            changeButtonFavorite(it.isFavorite)
            Log.d("favorite", it.isFavorite.toString())

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
            binding.view2.setImageDrawable(getDrawable(requireContext(),R.drawable.play))
        }else{
            binding.view2.setImageDrawable(getDrawable(requireContext(),R.drawable.pause))
        }
    }
    private fun enableButtonFavorite(isEnabled: Boolean){
        binding.view3.isEnabled=isEnabled
    }
    private fun changeButtonFavorite(isFavorite: Boolean){
        if(isFavorite){

            binding.view3.setImageDrawable(getDrawable(requireContext(),R.drawable.button))
        }
        else{
            binding.view3.setImageDrawable(getDrawable(requireContext(),R.drawable.like))
        }

    }
    override fun onPause() {
        super.onPause()
        viewModel.onPause()
    }
}