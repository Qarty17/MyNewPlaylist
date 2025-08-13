package com.example.mynewplaylist.settings.ui


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mynewplaylist.creator.Creator
import com.example.mynewplaylist.databinding.ActivitySettingsBinding
import com.example.mynewplaylist.settings.presentation.SettingsViewModel

class SettingsActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingsBinding
    private lateinit var viewModel: SettingsViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val switch= Creator.provideSwitchInteractor(this)
        val sharing= Creator.provideSharingInteractor(this)
        val theme= Creator.provideThemeInteractor(this)

        viewModel= ViewModelProvider(this, SettingsViewModel.getFactory(sharing,switch,theme))[SettingsViewModel::class.java]
        binding= ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.back.setOnClickListener {
            finish()
        }
        binding.switch1.isChecked=viewModel.getSwitch()
        binding.switch1.setOnCheckedChangeListener { switcher,checked->
            viewModel.saveSwitch(checked)
            (applicationContext as App).switchTheme(checked)
        }
        binding.share.setOnClickListener {
            viewModel.sharingAppLink()
        }
        binding.support.setOnClickListener{
            viewModel.openingSupport()

        }
        binding.agreement.setOnClickListener{
            viewModel.openingTerms()

        }
    }
}