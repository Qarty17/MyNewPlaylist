package com.example.mynewplaylist.main.ui

import android.R.id.button2
import android.R.id.button3
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.mynewplaylist.R
import com.example.mynewplaylist.databinding.ActivityMainBinding
import com.example.mynewplaylist.ui.MediaActivity
import com.example.mynewplaylist.ui.SearchActivity
import com.example.mynewplaylist.settings.ui.SettingsActivity

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.search.setOnClickListener{
            val button2Intent= Intent(this, SearchActivity::class.java)
            startActivity(button2Intent)
        }
        binding.media.setOnClickListener {
            val button3Intent= Intent(this, MediaActivity::class.java)
            startActivity(button3Intent)
        }
        binding.settings.setOnClickListener {
            val button1Intent= Intent(this, SettingsActivity::class.java)
            startActivity(button1Intent)
        }
    }

}