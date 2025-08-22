package com.example.mynewplaylist.media.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

import com.example.mynewplaylist.databinding.TrackFragmentBinding

class TracksFragment: Fragment() {
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
    }
}