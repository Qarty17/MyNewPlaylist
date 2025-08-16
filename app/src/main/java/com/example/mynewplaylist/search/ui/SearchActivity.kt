package com.example.mynewplaylist.search.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isNotEmpty
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mynewplaylist.creator.Creator
import com.example.mynewplaylist.databinding.ActivitySearchBinding
import com.example.mynewplaylist.search.domain.models.Track
import com.example.mynewplaylist.main.ui.MainActivity
import com.example.mynewplaylist.player.ui.AudioplayerActivity
import com.example.mynewplaylist.search.presentation.PlaylistViewModel
import com.example.mynewplaylist.search.presentation.TrackAdapter
import java.text.SimpleDateFormat
import java.util.Locale

class SearchActivity : AppCompatActivity(), TrackAdapter.Listener {

    private var simpleTextWatcher: TextWatcher? = null
    private val viewModel: PlaylistViewModel by viewModels()
    private lateinit var binding: ActivitySearchBinding
    private lateinit var historyAdapter: TrackAdapter
    private val tracks = ArrayList<Track>()
    private lateinit var adapter: TrackAdapter
    private val creator=Creator
    var newValue = VALUE_DEF
    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.observeSearch().observe (this){
            render(it.state!!)
            historyAdapter.tracks=it.history
        }

        binding= ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (savedInstanceState != null) {
            newValue = savedInstanceState.getString(VALUE, VALUE_DEF)
        }

        adapter= TrackAdapter(this) { track ->
            viewModel.onTrackClick(track)
        }
        binding.recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        adapter.tracks = tracks
        binding.recyclerView.adapter = adapter
        historyAdapter= TrackAdapter(this) { track ->
            if (viewModel.clickDebounce()) {
                viewModel.onTrackClick(track)
            }
        }
        binding.recyclerViewHistory.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.recyclerViewHistory.adapter = historyAdapter

        if (historyAdapter.tracks.isNotEmpty()){
            showContent(arrayListOf())

        }else{
            binding.history.visibility= View.GONE
        }

        binding.back2.setOnClickListener {
            val intent=Intent(this, MainActivity::class.java)
            startActivity(intent)
            //historyAdapter.tracks=method1()
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
                //showEmpty()
                showContent(arrayListOf())

            }
            binding.history.visibility =
            if (hasFocus && binding.inputEdittext.text.isEmpty() && historyAdapter.tracks.isNotEmpty()) View.VISIBLE else View.GONE

            historyAdapter.notifyDataSetChanged()
        }

        binding.clearIcon.setOnClickListener {
            val view: View? = this.currentFocus
            if (view != null) {
                val inputMethodManager =
                    getSystemService(INPUT_METHOD_SERVICE) as? InputMethodManager
                inputMethodManager?.hideSoftInputFromWindow(view.windowToken, 0)
            }
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
            creator.provideTrackInteractor()
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
        val intent= Intent(this, AudioplayerActivity::class.java)
        intent.putExtra(NAME,track.trackName)
        intent.putExtra(NAME_ARTIST,track.artistName)
        intent.putExtra(DURATION,SimpleDateFormat("mm:ss", Locale.getDefault()).format(track.trackTimeMillis))
        intent.putExtra(ALBUM,track.collectionName)
        intent.putExtra(YEAR,track.releaseDate)
        intent.putExtra(GENRE,track.primaryGenreName)
        intent.putExtra(COUNTRY,track.country)
        intent.putExtra(ARTWORK,track.artworkUrl100)
        intent.putExtra(PREVIEWURL,track.previewUrl)
        startActivity(intent)
    }
    companion object {
        const val NAME="name"
        const val VALUE = "VALUE"
        const val VALUE_DEF = ""
        const val NAME_ARTIST="name_artist"
        const val DURATION="duration"
        const val ALBUM="album"
        const val YEAR="year"
        const val GENRE="genre"
        const val COUNTRY="country"
        const val ARTWORK="artwork"
        const val PREVIEWURL="previewUrl"
    }
    override fun addName(track: Track): String{
        return track.trackName
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

    override fun onDestroy() {
        super.onDestroy()
        simpleTextWatcher.let { binding.inputEdittext.removeTextChangedListener(it) }
    }
}





