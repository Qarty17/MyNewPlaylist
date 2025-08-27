package com.example.mynewplaylist.player.ui

import android.R.attr.track
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.mynewplaylist.R
import com.example.mynewplaylist.databinding.FragmentAudioPlayerBinding
import com.example.mynewplaylist.player.presentation.PlayerViewModel
import com.example.mynewplaylist.search.domain.models.Track
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class AudioplayerFragment: Fragment() {
    private lateinit var url: String
    private lateinit var binding: FragmentAudioPlayerBinding
    private val viewModel: PlayerViewModel by viewModel<PlayerViewModel>{
        parametersOf(url)
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
        fun createArgs(nameTrack: String,
                       artistTrack: String,
                       duration: String,
                       album: String,
                       genre: String,
                       country: String,
                       year: String,
                       artworkUrl:String,
                       previewUrl:String

        ): Bundle=
            bundleOf(NAME to nameTrack,
                ARTIST to artistTrack,
                DURATION to duration,
                ALBUM to album,
                GENRE to genre,
                COUNTRY to country,
                YEAR to year,
                ARTWORKURL to artworkUrl,
                PREVIEWURL to previewUrl)
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
        viewModel.observePlayerProgressLiveData().observe(viewLifecycleOwner) {
            changeButton(it.player== PlayerViewModel.STATE_PLAYING)
            enableButton(it.player!= PlayerViewModel.STATE_DEFAULT)
            binding.timer.text=it.progress
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
            binding.view2.setImageDrawable(getDrawable(requireContext(),R.drawable.play))
        }else{
            binding.view2.setImageDrawable(getDrawable(requireContext(),R.drawable.pause))
        }
    }
    override fun onPause() {
        super.onPause()
        viewModel.onPause()
    }
}