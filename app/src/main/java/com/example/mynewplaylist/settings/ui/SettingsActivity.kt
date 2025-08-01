package com.example.mynewplaylist.settings.ui

import android.content.Intent

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.mynewplaylist.R
import com.example.mynewplaylist.creator.Creator
import com.example.mynewplaylist.presentation.App

import androidx.core.net.toUri
import com.example.mynewplaylist.databinding.ActivitySettingsBinding

class SettingsActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val manager= Creator.provideSwitchInteractor(this)
        val manager2= Creator.provideSharingInteractor()
        binding= ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.back.setOnClickListener {
            finish()
        }
        binding.switch1.isChecked=manager.getSavedSwitcher()
        binding.switch1.setOnCheckedChangeListener { switcher,checked->
            manager.saveSwitcher(checked)
            (applicationContext as App).switchTheme(checked)


        }
        binding.share.setOnClickListener {
            intent=manager2.shareApp()
            startActivity(Intent.createChooser(intent,"Share to"))
//            val intent= Intent()
//            intent.action= Intent.ACTION_SEND
//            intent.putExtra(Intent.EXTRA_TEXT,"https://practicum.yandex.ru/learn/android-developer-plus")
//            intent.type="text/plain"
//            startActivity(Intent.createChooser(intent,"Share to"))
        }
        binding.support.setOnClickListener{
//            val subject=getString(R.string.message_developer)
//            val message=getString(R.string.thanks)
//            val supportIntent= Intent(Intent.ACTION_SENDTO)
//            supportIntent.data= "mailto:".toUri()
//            supportIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf("yourEmail@ya.ru"))
//            supportIntent.putExtra(Intent.EXTRA_SUBJECT,subject)
//            supportIntent.putExtra(Intent.EXTRA_TEXT,message)
            val supportIntent=manager2.openSupport()
            startActivity(supportIntent)
        }
        binding.agreement.setOnClickListener{
            val agreementIntent=manager2.openTerms()
            startActivity(agreementIntent)
        }
    }
}