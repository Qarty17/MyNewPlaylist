package com.example.mynewplaylist.settings.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.mynewplaylist.settings.domain.api.SwitchInteractor
import com.example.mynewplaylist.settings.domain.api.ThemeInteractor
import com.example.mynewplaylist.sharing.domain.SharingInteractor

class SettingsViewModel(private val sharingInteractor: SharingInteractor, private val switchInteractor: SwitchInteractor, private val themeInteractor: ThemeInteractor): ViewModel(){

    fun sharingAppLink(){
        sharingInteractor.shareApp()
    }
    fun openingSupport(){
        sharingInteractor.openSupport()
    }
    fun openingTerms(){
        sharingInteractor.openTerms()
    }
    fun getSwitch(): Boolean{
        return switchInteractor.getSavedSwitcher()
    }
    fun saveSwitch(isTrue: Boolean){
        switchInteractor.saveSwitcher(isTrue)
    }
//    fun getTheme(): Boolean{
//        return themeInteractor.getSavedTheme()
//    }
//    fun saveTheme(isDarkTheme:Boolean){
//        themeInteractor.saveTheme(isDarkTheme)
//    }

    companion object{
        fun getFactory(sharing: SharingInteractor, switch: SwitchInteractor, theme: ThemeInteractor): ViewModelProvider.Factory=
            viewModelFactory {
                initializer {
                    SettingsViewModel(sharing, switch, theme)
                }
            }
    }
}