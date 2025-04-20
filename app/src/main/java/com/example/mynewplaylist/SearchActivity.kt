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

class SearchActivity : AppCompatActivity() {
    @SuppressLint("ServiceCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_search)
        var newValue= VALUE_DEF
        if(savedInstanceState!=null){
            newValue=savedInstanceState.getString(VALUE, VALUE_DEF)
        }
        val backButton=findViewById<Button>(R.id.back2)
        backButton.setOnClickListener{
            val backIntent=Intent(this,MainActivity::class.java)
            startActivity(backIntent)
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