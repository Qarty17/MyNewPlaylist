package com.example.mynewplaylist

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class SearchActivity : AppCompatActivity() {
    @SuppressLint("ServiceCast")
    var newValue= VALUE_DEF
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        if(savedInstanceState!=null){
            newValue=savedInstanceState.getString(VALUE, VALUE_DEF)
        }
        val backButton=findViewById<Button>(R.id.back2)
        backButton.setOnClickListener{
            finish()

        }
        val inputEditText=findViewById<EditText>(R.id.input_edittext)

        val clearButton=findViewById<ImageView>(R.id.clearIcon)
        clearButton.setOnClickListener{
            val view:View?=this.currentFocus
            if (view!=null){
                val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
                inputMethodManager?.hideSoftInputFromWindow(view.windowToken, 0)
            }

            inputEditText.setText("")
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
        val listTrack=listOf<Track>(
            Track(getString(R.string.track_name1),getString(R.string.track_autor1),getString(R.string.track_time1),getString(R.string.artwork_url1)) ,
            Track(getString(R.string.track_name2),getString(R.string.track_autor2),getString(R.string.track_time2),getString(R.string.artwork_url2)),            Track("Stayin' Alive","Bee Gees","4:10","https://is4-ssl.mzstatic.com/image/thumb/Music115/v4/1f/80/1f/1f801fc1-8c0f-ea3e-d3e5-387c6619619e/16UMGIM86640.rgb.jpg/100x100bb.jpg"),
            Track(getString(R.string.track_name3),getString(R.string.track_autor3),getString(R.string.track_time3),getString(R.string.artwork_url3)),
            Track(getString(R.string.track_name4),getString(R.string.track_autor4),getString(R.string.track_time4),getString(R.string.artwork_url4)),
            Track(getString(R.string.track_name5),getString(R.string.track_autor5),getString(R.string.track_time5),getString(R.string.artwork_url5)))
        val recycleView=findViewById<RecyclerView>(R.id.recyclerView)
        recycleView.layoutManager= LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)
        recycleView.adapter= TrackAdapter(
            tracks = listTrack
        )
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
}