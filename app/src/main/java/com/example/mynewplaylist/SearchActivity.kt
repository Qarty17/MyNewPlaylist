package com.example.mynewplaylist

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SearchActivity : AppCompatActivity() {
    private lateinit var backButton: Button
    private lateinit var inputEditText: EditText
    private lateinit var clearButton: ImageView
    private lateinit var recycleView: RecyclerView
    private lateinit var notFound: LinearLayout
    private lateinit var notInternet: LinearLayout
    private lateinit var updateButton: Button
    private val playlistBaseUrl="https://itunes.apple.com"
    private val retrofit= Retrofit.Builder()
        .baseUrl(playlistBaseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private var playlistService=retrofit.create(PlaylistApi::class.java)
    private val tracks=ArrayList<Track>()
    private val adapter= TrackAdapter()
    var newValue= VALUE_DEF
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        if(savedInstanceState!=null){
            newValue=savedInstanceState.getString(VALUE, VALUE_DEF)
        }
        backButton=findViewById<Button>(R.id.back2)
        backButton.setOnClickListener{
            finish()
        }
        inputEditText=findViewById<EditText>(R.id.input_edittext)
        inputEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                search()
                true
            }
            false
        }
        clearButton=findViewById<ImageView>(R.id.clearIcon)
        clearButton.setOnClickListener{
            val view:View?=this.currentFocus
            if (view!=null){
                val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
                inputMethodManager?.hideSoftInputFromWindow(view.windowToken, 0)
            }
            inputEditText.setText("")
            tracks.clear()
            adapter.notifyDataSetChanged()
            notFound.visibility=View.GONE
            notInternet.visibility= View.GONE

        }
        val simpleTextWatcher=object:TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                newValue=s.toString()
                clearButton.visibility=clearButtonVisibly(s)
            }
            override fun afterTextChanged(s: Editable?) {
            }
        }
        inputEditText.addTextChangedListener(simpleTextWatcher)
        recycleView=findViewById<RecyclerView>(R.id.recyclerView)
        recycleView.layoutManager= LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)
        adapter.tracks=tracks
        recycleView.adapter= adapter
        notFound=findViewById<LinearLayout>(R.id.not_found)
        notInternet=findViewById<LinearLayout>(R.id.not_internet)
        updateButton=findViewById<Button>(R.id.update_button)
        updateButton.setOnClickListener {
            search()
        }
    }

    private fun clearButtonVisibly(s:CharSequence?):Int {
        return if (s.isNullOrEmpty()) {
            View.GONE
        }
        else{
            View.VISIBLE
        }
    }

    companion object {
        const val VALUE = "VALUE"
        const val VALUE_DEF = ""
    }
    private fun search(){
        playlistService.search(inputEditText.text.toString()).enqueue(object : Callback<PlaylistResponse>{
            override fun onResponse(
                call: Call<PlaylistResponse?>,
                response: Response<PlaylistResponse?>
            ) {
                if(response.code()==200){
                    tracks.clear()

                    if(response.body()?.results?.isNotEmpty()==true){
                        tracks.addAll(response?.body()?.results!!)
                    }else{
                        notFound.visibility= View.VISIBLE
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
                notInternet.visibility= View.VISIBLE
            }

        })
    }

}