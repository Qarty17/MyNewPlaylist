package com.example.mynewplaylist

import android.content.Intent
import android.os.Bundle

import android.widget.Button


import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        val button1=findViewById<Button>(R.id.search)
        button1.setOnClickListener{
            val button2Intent=Intent(this,SearchActivity::class.java)
            startActivity(button2Intent)
        }
        val button2=findViewById<Button>(R.id.media)
        button2.setOnClickListener {
            val button3Intent=Intent(this,MediaActivity::class.java)
            startActivity(button3Intent)
        }
        val button3=findViewById<Button>(R.id.settings)
        button3.setOnClickListener {
            val button1Intent=Intent(this,SettingsActivity::class.java)
            startActivity(button1Intent)
        }

    }
}