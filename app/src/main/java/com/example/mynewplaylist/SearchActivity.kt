package com.example.mynewplaylist

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.Locale

const val new_key="key_from_list"
class SearchActivity : AppCompatActivity(), TrackAdapter.Listener {
    private val searchRunnable= Runnable { search() }
    private var isClickAllowed = true
    private val handler:Handler =Handler(Looper.getMainLooper())
    private lateinit var progressBar: ProgressBar
    private lateinit var backButton: Button
    private lateinit var inputEditText: EditText
    private lateinit var clearButton: ImageView
    private lateinit var recycleView: RecyclerView
    private lateinit var historyRecycleView: RecyclerView
    private lateinit var notFound: LinearLayout
    private lateinit var history: LinearLayout
    private lateinit var historyAdapter: TrackAdapter
    private lateinit var notInternet: LinearLayout
    private lateinit var updateButton: Button
    private lateinit var clearHistory: Button
    lateinit var searchHistory: SearchHistory
    private val playlistBaseUrl = "https://itunes.apple.com"
    private val retrofit = Retrofit.Builder()
        .baseUrl(playlistBaseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private var playlistService = retrofit.create(PlaylistApi::class.java)
    private val tracks = ArrayList<Track>()
    private lateinit var adapter: TrackAdapter

    var newValue = VALUE_DEF
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_search)

        if (savedInstanceState != null) {
            newValue = savedInstanceState.getString(VALUE, VALUE_DEF)
        }
        progressBar=findViewById(R.id.progress_bar)
        backButton = findViewById<Button>(R.id.back2)
        inputEditText = findViewById<EditText>(R.id.input_edittext)
        clearButton = findViewById<ImageView>(R.id.clearIcon)
        recycleView = findViewById<RecyclerView>(R.id.recyclerView)
        notFound = findViewById<LinearLayout>(R.id.not_found)
        notInternet = findViewById<LinearLayout>(R.id.not_internet)
        updateButton = findViewById<Button>(R.id.update_button)
        history = findViewById<LinearLayout>(R.id.history)
        clearHistory = findViewById<Button>(R.id.history_button)
        historyRecycleView = findViewById<RecyclerView>(R.id.recyclerViewHistory)
        searchHistory= SearchHistory(this)
        adapter= TrackAdapter(this){track->
            searchHistory.onTrackClick(track)
        }
        recycleView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        adapter.tracks = tracks
        recycleView.adapter = adapter
        historyAdapter= TrackAdapter(this){track->
            if(clickDebounce()){
                searchHistory.onTrackClick(track)
            }

        }
        historyRecycleView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        historyRecycleView.adapter = historyAdapter
        historyAdapter.tracks = searchHistory.getHistory()
        if (searchHistory.getHistory().isNotEmpty()){
            history.visibility= View.VISIBLE
        }else{
            history.visibility= View.GONE
        }
        backButton.setOnClickListener {
            val intent=Intent(this,MainActivity::class.java)
            startActivity(intent)
            historyAdapter.tracks=method1()
        }
        inputEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                search()
                true
            }
            false
        }

        val simpleTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if(inputEditText.text.isNotEmpty()){
                    progressBar.visibility=View.VISIBLE
                    searchDebounce()
                }
                history.visibility = if (inputEditText.hasFocus() && p0?.isEmpty() == true && searchHistory.getHistory().isNotEmpty()) {


                    recycleView.visibility = View.GONE
                    historyAdapter.tracks=searchHistory.getHistory()
                    historyAdapter.notifyDataSetChanged()
                    adapter.notifyDataSetChanged()
                    View.VISIBLE
                } else {
                    recycleView.visibility = View.GONE
                    View.GONE
                }

                newValue = p0.toString()
                clearButton.visibility = clearButtonVisibly(p0)
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        }
        inputEditText.addTextChangedListener(simpleTextWatcher)
        inputEditText.setOnFocusChangeListener { view, hasFocus ->

            history.visibility =
                if (hasFocus && inputEditText.text.isEmpty() && searchHistory.getHistory().isNotEmpty()) View.VISIBLE else View.GONE

        }

        clearButton.setOnClickListener {
            val view: View? = this.currentFocus
            if (view != null) {
                val inputMethodManager =
                    getSystemService(INPUT_METHOD_SERVICE) as? InputMethodManager
                inputMethodManager?.hideSoftInputFromWindow(view.windowToken, 0)
            }
            inputEditText.setText("")
            tracks.clear()
            adapter.notifyDataSetChanged()
            if (searchHistory.getHistory().isNotEmpty()){
                history.visibility= View.VISIBLE
            }else{
                history.visibility= View.GONE
            }
            historyAdapter.tracks=searchHistory.getHistory()
            historyAdapter.notifyDataSetChanged()

            notFound.visibility = View.GONE
            notInternet.visibility = View.GONE

        }
        updateButton.setOnClickListener {
            search()
        }
        clearHistory.setOnClickListener {
            searchHistory.clearHistory()
            historyAdapter.tracks.clear()
            historyAdapter.notifyDataSetChanged()
            history.visibility=View.GONE

        }
    }

    private fun clickDebounce():Boolean{
        val current=isClickAllowed
        if(isClickAllowed){
            isClickAllowed=false
            handler.postDelayed({isClickAllowed=true},1000L)
        }
        return current
    }
    private fun searchDebounce(){
        handler.removeCallbacks(searchRunnable)
        handler.postDelayed(searchRunnable,2000L)


    }
    private fun clearButtonVisibly(s: CharSequence?): Int {

        return if (s.isNullOrEmpty()) {
            View.GONE
        } else {
            View.VISIBLE
        }
    }

    

    private fun search() {
        progressBar.visibility=View.VISIBLE
        playlistService.search(inputEditText.text.toString())
            .enqueue(object : Callback<PlaylistResponse> {
                override fun onResponse(
                    call: Call<PlaylistResponse?>,
                    response: Response<PlaylistResponse?>
                ) {
                    if(inputEditText.text.isNotEmpty()){
                        if (response.code() == 200) {
                            tracks.clear()

                            if (response.body()?.results?.isNotEmpty() == true) {
                                progressBar.visibility=View.GONE
                                tracks.addAll(response.body()?.results!!)
                                recycleView.visibility = View.VISIBLE
                            } else {
                                progressBar.visibility=View.GONE
                                recycleView.visibility = View.GONE
                                history.visibility = View.GONE
                                notFound.visibility = View.VISIBLE


                            }
                            adapter.notifyDataSetChanged()
                        }
                    }

                }

                override fun onFailure(
                    call: Call<PlaylistResponse?>,
                    t: Throwable
                ) {

                    tracks.clear()
                    adapter.notifyDataSetChanged()
                    recycleView.visibility = View.GONE
                    history.visibility = View.GONE
                    notInternet.visibility = View.VISIBLE
                }

            })
    }

//    fun isNetwork(context: Context): Boolean {
//        val cm = context
//            .getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
//        val netInfo = cm.getActiveNetworkInfo()
//        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
//            return true
//        }
//        return false
//    }
private fun method1(): ArrayList<Track>{
        searchHistory= SearchHistory(this)
        return searchHistory.getHistory()
    }

    override fun onClick(track: Track) {
        val intent= Intent(this, AudioplayerActivity::class.java)
        intent.putExtra(NAME,track.trackName)
        intent.putExtra(NAME_ARTIST,track.artistName)
        intent.putExtra(DURATION,SimpleDateFormat("mm:ss", Locale.getDefault()).format(track.trackTimeMillis))
        intent.putExtra(ALBUM,track.collectionName)
        intent.putExtra(YEAR,track.releaseDate)
        //intent.putExtra("year",SimpleDateFormat("yyyy", Locale.getDefault()).format(track.releaseDate))
        intent.putExtra(GENRE,track.primaryGenreName)
        intent.putExtra(COUNTRY,track.country)
        intent.putExtra(ARTWORK,track.artworkUrl100)
        intent.putExtra("previewUrl",track.previewUrl)
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
}



