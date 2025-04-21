package com.example.mynewplaylist

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        val backButton = findViewById<Button>(R.id.back)
        backButton.setOnClickListener {
            finish()
        }
        val share = findViewById<Button>(R.id.share)
        share.setOnClickListener {
            val intent= Intent()
            intent.action=Intent.ACTION_SEND
            intent.putExtra(Intent.EXTRA_TEXT,"https://practicum.yandex.ru/learn/android-developer-plus")
            intent.type="text/plain"
            startActivity(Intent.createChooser(intent,"Share to"))
        }
        val supportButton = findViewById<Button>(R.id.support)
        supportButton.setOnClickListener{
            val subject="Сообщение разработчикам и разработчицам приложения Playlist Maker"
            val message="Спасибо разработчикам и разработчицам за крутое приложение!"
            val supportIntent=Intent(Intent.ACTION_SENDTO)
            supportIntent.data= "mailto:".toUri()
            supportIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf("yourEmail@ya.ru"))
            supportIntent.putExtra(Intent.EXTRA_SUBJECT,subject)
            supportIntent.putExtra(Intent.EXTRA_TEXT,message)
            startActivity(supportIntent)
        }

        val userAgreement=findViewById<Button>(R.id.agreement)
        userAgreement.setOnClickListener{
            val url= "https://yandex.ru/legal/practicum_offer/".toUri()
            val agreementIntent=Intent(Intent.ACTION_VIEW,url)

            startActivity(agreementIntent)
        }
    }
}
