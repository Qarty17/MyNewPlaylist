package com.example.mynewplaylist

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.collection.ArraySet
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import androidx.core.content.edit
import androidx.lifecycle.VIEW_MODEL_STORE_OWNER_KEY

const val new_key="key_from_list"
class SearchActivity : AppCompatActivity() {
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
        adapter= TrackAdapter{track->
            searchHistory.onTrackClick(track)
        }
        recycleView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        adapter.tracks = tracks
        recycleView.adapter = adapter
        historyAdapter= TrackAdapter{track->
            searchHistory.onTrackClick(track)
        }
        historyRecycleView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        historyRecycleView.adapter = historyAdapter
        historyAdapter.tracks = searchHistory.getHistory()
        backButton.setOnClickListener {
            finish()
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

                history.visibility = if (inputEditText.hasFocus() && p0?.isEmpty() == true && searchHistory.getHistory()!=null) {
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
                if (hasFocus && inputEditText.text.isEmpty() && searchHistory.getHistory()!=null) View.VISIBLE else View.GONE

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
            if (searchHistory.getHistory()!=null){
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
//        val sharedPreferences = getSharedPreferences("list", MODE_PRIVATE)
//        listener = SharedPreferences.OnSharedPreferenceChangeListener { sharedPreferences, key ->
//            if (key == new_key) {
//                val track = sharedPreferences?.getString(new_key, null)
//                if (track != null) {
//                    historyAdapter.tracks.add(0, createTrackFromJson(track))
//                    adapter.notifyItemInserted(0)
//                }
//
//            }
//        }
//        sharedPreferences.registerOnSharedPreferenceChangeListener(listener)


    }
    private fun clearButtonVisibly(s: CharSequence?): Int {
        return if (s.isNullOrEmpty()) {
            View.GONE
        } else {
            View.VISIBLE
        }
    }

    companion object {
        const val VALUE = "VALUE"
        const val VALUE_DEF = ""
    }

    private fun search() {
        playlistService.search(inputEditText.text.toString())
            .enqueue(object : Callback<PlaylistResponse> {
                override fun onResponse(
                    call: Call<PlaylistResponse?>,
                    response: Response<PlaylistResponse?>
                ) {
                    if (response.code() == 200) {
                        tracks.clear()

                        if (response.body()?.results?.isNotEmpty() == true) {
                            tracks.addAll(response.body()?.results!!)
                            recycleView.visibility = View.VISIBLE
                        } else {
                            recycleView.visibility = View.GONE
                            history.visibility = View.GONE
                            notFound.visibility = View.VISIBLE
                        }
                        adapter.notifyDataSetChanged()
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


    fun method1(): ArrayList<Track>{
        searchHistory= SearchHistory(this)
        return searchHistory.getHistory()
    }
}
//    override fun onClick(track: Track) {
//        Toast.makeText(this,"Save", Toast.LENGTH_LONG).show()
//        historyTracks.removeAll { it.trackId == track.trackId }
//        historyTracks.add(0, track)
//        if (historyTracks.size > 9)
//        {        historyTracks.subList(9, historyTracks.size).clear()    }
////        sharedPreferences.edit {
////            putString(new_key, createJsonFromTrack(track))
////        }
//    }
//    private fun createJsonFromTrack(track: Track): String {
//        return Gson().toJson(track)
//    }
//
    private fun createTrackFromJson(json: String): Track {
        return Gson().fromJson(json, Track::class.java)
    }
//}


