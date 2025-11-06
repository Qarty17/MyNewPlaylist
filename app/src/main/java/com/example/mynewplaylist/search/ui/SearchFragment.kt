package com.example.mynewplaylist.search.ui

import android.annotation.SuppressLint
import android.content.Context.INPUT_METHOD_SERVICE
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope

import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mynewplaylist.R
import com.example.mynewplaylist.databinding.FragmentSearchBinding

import com.example.mynewplaylist.player.ui.AudioplayerFragment
import com.example.mynewplaylist.search.domain.models.Track
import com.example.mynewplaylist.search.presentation.PlaylistViewModel
import com.example.mynewplaylist.search.presentation.TrackAdapter
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.Locale
import kotlin.getValue
import kotlin.toString

class SearchFragment: Fragment(), TrackAdapter.Listener {
    private var simpleTextWatcher: TextWatcher? = null
    private val viewModel: PlaylistViewModel by viewModel()
    private lateinit var historyAdapter: TrackAdapter
    private var isClickAllowed = true
    private val tracks = ArrayList<Track>()
    private lateinit var adapter: TrackAdapter
    var newValue = VALUE_DEF
    private lateinit var binding: FragmentSearchBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentSearchBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.observeSearch().observe (viewLifecycleOwner){
            render(it.state!!)

            historyAdapter.tracks=it.history
        }
        if (savedInstanceState != null) {
            newValue = savedInstanceState.getString(VALUE, VALUE_DEF)
        }

        adapter= TrackAdapter(this) { track ->
            viewModel.onTrackClick(track)
        }
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        adapter.tracks = tracks
        binding.recyclerView.adapter = adapter
        historyAdapter= TrackAdapter(this) { track ->
            if (clickDebounce()) {
                viewModel.onTrackClick(track)
            }
        }
        binding.recyclerViewHistory.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.recyclerViewHistory.adapter = historyAdapter

        if (historyAdapter.tracks.isNotEmpty()){
            showContent(arrayListOf())

        }else{
            binding.history.visibility= View.GONE
        }

        binding.inputEdittext.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                true
            }
            false
        }
        simpleTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            @SuppressLint("NotifyDataSetChanged")
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if(p0?.isNotEmpty() == true){
                    binding.apply {
                        showLoading()
                    }
                    viewModel.searchDebounce(changedText = p0.toString())
                    historyAdapter.notifyDataSetChanged()
                    adapter.notifyDataSetChanged()

                }else{
                    viewModel.clearHandler()
                    viewModel.clearText()
                    binding.history.visibility = if (p0?.isEmpty() == true && historyAdapter.tracks.isNotEmpty()) {
                        binding.apply {
                            recyclerView.visibility = View.GONE
                            notFound.visibility=View.GONE
                            notInternet.visibility=View.GONE
                            progressBar.visibility=View.GONE
                        }
                        historyAdapter.notifyDataSetChanged()
                        adapter.notifyDataSetChanged()
                        View.VISIBLE

                    } else {
                        binding.apply {
                            recyclerView.visibility = View.GONE
                            notFound.visibility=View.GONE
                            notInternet.visibility=View.GONE
                            progressBar.visibility=View.GONE
                        }
                        View.GONE
                    }
                }
                historyAdapter.notifyDataSetChanged()
                adapter.notifyDataSetChanged()
                newValue = p0.toString()
                binding.clearIcon.visibility = clearButtonVisibly(p0)
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        }
        simpleTextWatcher.let { binding.inputEdittext.addTextChangedListener(it) }
        binding.inputEdittext.setOnFocusChangeListener { view, hasFocus ->
            if(binding.inputEdittext.text.isEmpty()){
                showContent(arrayListOf())
            }
            binding.history.visibility =
                if (hasFocus && binding.inputEdittext.text.isEmpty() && historyAdapter.tracks.isNotEmpty()) View.VISIBLE else View.GONE

            historyAdapter.notifyDataSetChanged()
        }
        binding.clearIcon.setOnClickListener {
            val view: View? = activity?.currentFocus
            if (view != null) {
                val inputMethodManager =
                    requireContext().getSystemService(INPUT_METHOD_SERVICE) as? InputMethodManager
                inputMethodManager?.hideSoftInputFromWindow(view.windowToken, 0)
            }
            viewModel.clearText()
            binding.inputEdittext.setText("")
            tracks.clear()
            adapter.notifyDataSetChanged()
            if (historyAdapter.tracks.isNotEmpty()){
                binding.history.visibility= View.VISIBLE
            }else{
                binding.history.visibility= View.GONE
            }
            binding.progressBar.visibility=View.GONE
            historyAdapter.notifyDataSetChanged()
            binding.notFound.visibility = View.GONE
            binding.notInternet.visibility = View.GONE


        }
        binding.updateButton.setOnClickListener {
            showLoading()
            viewModel.searchDebounce(binding.inputEdittext.text.toString())
        }
        binding.historyButton.setOnClickListener {
            viewModel.clearHistory()
            historyAdapter.tracks.clear()
            historyAdapter.notifyDataSetChanged()
            binding.history.visibility=View.GONE

        }
    }
    private fun clearButtonVisibly(s: CharSequence?): Int {

        return if (s.isNullOrEmpty()) {
            View.GONE
        } else {
            View.VISIBLE
        }
    }
    override fun onClick(track: Track) {
        findNavController().navigate(R.id.action_searchFragment_to_audioplayerFragment,
            AudioplayerFragment.createArgs(
                track.trackName,
                track.artistName,
                track.trackTimeMillis,
                //SimpleDateFormat("mm:ss", Locale.getDefault()).format(track.trackTimeMillis),
                track.collectionName,
                track.primaryGenreName,
                track.country,
                track.releaseDate,
                track.artworkUrl100,
                track.previewUrl,
                track.isFavorite,
                track.trackId
            ))
    }
    companion object {
        const val VALUE = "VALUE"
        const val VALUE_DEF = ""

    }
    override fun addName(track: Track): String{
        return track.trackName
    }
    fun clickDebounce():Boolean{
        val current=isClickAllowed
        if(isClickAllowed){
            isClickAllowed=false
            viewLifecycleOwner.lifecycleScope.launch {
                delay(1000L)
                isClickAllowed=true
            }
        }
        return current
    }
    fun showLoading(){
        binding.apply {
            notInternet.visibility= View.GONE
            recyclerView.visibility= View.GONE
            progressBar.visibility=View.VISIBLE
            history.visibility=View.GONE
            notFound.visibility=View.GONE
        }
    }
    @SuppressLint("NotifyDataSetChanged")
    fun showContent(tracksList: ArrayList<Track>){
        binding.apply {

            recyclerView.visibility=View.VISIBLE


            notFound.visibility=View.GONE
            notInternet.visibility=View.GONE
            progressBar.visibility=View.GONE
            if(inputEdittext.text.isEmpty() && historyAdapter.tracks.isNotEmpty()){
                Log.d("HISTORY", historyAdapter.tracks.toString())
                history.visibility=View.VISIBLE
            }
            else{
                history.visibility=View.GONE
            }

        }
        historyAdapter.notifyDataSetChanged()
        adapter.tracks.clear()
        adapter.tracks.addAll(tracksList)
        adapter.notifyDataSetChanged()

    }
    fun showError(){
        binding.apply {
            recyclerView.visibility=View.GONE
            notFound.visibility=View.GONE
            notInternet.visibility=View.VISIBLE
            progressBar.visibility=View.GONE
            history.visibility=View.GONE
        }

    }
    fun showEmpty(){
        binding.apply {
            recyclerView.visibility=View.GONE
            notFound.visibility=View.VISIBLE
            notInternet.visibility=View.GONE
            progressBar.visibility=View.GONE
            history.visibility=View.GONE
        }
    }
    fun render(state: PlaylistState){
        when(state){
            is PlaylistState.Loading->showLoading()
            is PlaylistState.Content->showContent(state.tracks)
            is PlaylistState.Error->showError()
            is PlaylistState.Empty->showEmpty()

        }
    }
}