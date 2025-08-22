package com.example.mynewplaylist.settings.ui


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.mynewplaylist.databinding.ActivitySettingsBinding
import com.example.mynewplaylist.settings.presentation.SettingsViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class SettingsActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingsBinding
    private val viewModel: SettingsViewModel by viewModel<SettingsViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.back.setOnClickListener {
            finish()
        }
        viewModel.observeSwitch().observe(this){
            binding.switch1.isChecked=it
        }
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