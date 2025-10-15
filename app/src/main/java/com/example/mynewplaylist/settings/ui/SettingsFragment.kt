package com.example.mynewplaylist.settings.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import com.example.mynewplaylist.databinding.FragmentSettingsBinding
import com.example.mynewplaylist.settings.presentation.SettingsViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.getValue

class SettingsFragment: Fragment() {
    private val viewModel: SettingsViewModel by viewModel<SettingsViewModel>()
    private lateinit var binding:FragmentSettingsBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentSettingsBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.observeSwitch().observe(viewLifecycleOwner){
            binding.switch1.isChecked=it
        }
        binding.switch1.setOnCheckedChangeListener { switcher,checked->
            viewModel.saveSwitch(checked)
            ((requireContext().applicationContext) as App).switchTheme(checked)
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