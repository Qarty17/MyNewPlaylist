package com.example.mynewplaylist.search.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity

import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mynewplaylist.creator.Creator
import com.example.mynewplaylist.databinding.ActivitySearchBinding
import com.example.mynewplaylist.search.domain.models.Track
import com.example.mynewplaylist.main.ui.MainActivity
import com.example.mynewplaylist.player.ui.AudioplayerActivity



import java.text.SimpleDateFormat
import java.util.Locale
const val new_key="key_from_list"
class SearchActivity : AppCompatActivity(), TrackAdapter.Listener {
    private var isClickAllowed = true
    private var simpleTextWatcher: TextWatcher? = null
    private var viewModel: PlaylistViewModel?=null
    private lateinit var binding: ActivitySearchBinding
    private lateinit var historyAdapter: TrackAdapter
    lateinit var searchHistory: SearchHistory
    private val tracks = ArrayList<Track>()
    private lateinit var adapter: TrackAdapter
    private val creator=Creator
    var newValue = VALUE_DEF
    private val handler:Handler =Handler(Looper.getMainLooper())
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel= ViewModelProvider(this, PlaylistViewModel.getFactory())[PlaylistViewModel::class.java]
        viewModel?.observeState()?.observe(this){
            render(it)
        }
        binding= ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (savedInstanceState != null) {
            newValue = savedInstanceState.getString(VALUE, VALUE_DEF)
        }
        searchHistory= SearchHistory(this)
        adapter= TrackAdapter(this){track->
            searchHistory.onTrackClick(track)
        }
        binding.recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        adapter.tracks = tracks
        binding.recyclerView.adapter = adapter
        historyAdapter= TrackAdapter(this){track->
            if(clickDebounce() ){
                searchHistory.onTrackClick(track)
            }
        }
        binding.recyclerViewHistory.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.recyclerViewHistory.adapter = historyAdapter
        historyAdapter.tracks = searchHistory.manager.getHistory()
        if (searchHistory.manager.getHistory().isNotEmpty()){
            showHistory()
        }else{
            binding.history.visibility= View.GONE
        }
        binding.back2.setOnClickListener {
            val intent=Intent(this, MainActivity::class.java)
            startActivity(intent)
            historyAdapter.tracks=method1()
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

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if(p0?.isNotEmpty() == true){
                    binding.apply {
                        showLoading()
                    }
                    viewModel?.searchDebounce(changedText = p0.toString())
                    historyAdapter.notifyDataSetChanged()
                    adapter.notifyDataSetChanged()


                }else{
                    viewModel?.clearHandler()
                    binding.history.visibility = if (binding.inputEdittext.hasFocus() && p0?.isEmpty() == true && searchHistory.manager.getHistory().isNotEmpty()) {
                        binding.progressBar.visibility=View.GONE
                        binding.recyclerView.visibility = View.GONE
                        historyAdapter.tracks=searchHistory.manager.getHistory()
                        historyAdapter.notifyDataSetChanged()
                        adapter.notifyDataSetChanged()

                        View.VISIBLE
                    } else {
                        binding.recyclerView.visibility = View.GONE
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
                showHistory()
            }
            binding.history.visibility =
            if (hasFocus && binding.inputEdittext.text.isEmpty() && searchHistory.manager.getHistory().isNotEmpty()) View.VISIBLE else View.GONE


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
            if (searchHistory.manager.getHistory().isNotEmpty()){
                binding.history.visibility= View.VISIBLE
            }else{
                binding.history.visibility= View.GONE
            }
            binding.progressBar.visibility=View.GONE
            historyAdapter.tracks=searchHistory.manager.getHistory()
            historyAdapter.notifyDataSetChanged()

            binding.notFound.visibility = View.GONE
            binding.notInternet.visibility = View.GONE

        }
        binding.updateButton.setOnClickListener {
            showLoading()
            viewModel?.searchDebounce(binding.inputEdittext.text.toString())
            creator.provideTrackInteractor(this)
        }
        binding.historyButton.setOnClickListener {
            searchHistory.manager.clearHistory()
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
    private fun method1(): ArrayList<Track>{
        searchHistory= SearchHistory(this)
        return searchHistory.manager.getHistory()
    }
    private fun clickDebounce():Boolean{
        val current=isClickAllowed
        if(isClickAllowed){
            isClickAllowed=false
            handler.postDelayed({isClickAllowed=true},1000L)
        }
        return current
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
    fun showHistory(){
        binding.apply {
            notInternet.visibility= View.GONE
            recyclerView.visibility= View.GONE
            progressBar.visibility=View.GONE
            history.visibility=View.VISIBLE
            notFound.visibility=View.GONE

        }
        historyAdapter.notifyDataSetChanged()
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
    fun showContent(tracksList: ArrayList<Track>){
        binding.apply {
            recyclerView.visibility=View.VISIBLE
            notFound.visibility=View.GONE
            notInternet.visibility=View.GONE
            progressBar.visibility=View.GONE
            history.visibility=View.GONE
        }
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



