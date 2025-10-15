package com.example.mynewplaylist.settings.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mynewplaylist.settings.domain.api.SwitchInteractor
import com.example.mynewplaylist.sharing.domain.SharingInteractor

class SettingsViewModel(private val sharingInteractor: SharingInteractor, private val switchInteractor: SwitchInteractor): ViewModel(){

    private val switchLiveData= MutableLiveData<Boolean>(switchInteractor.getSavedSwitcher())
    fun observeSwitch():LiveData<Boolean> = switchLiveData

    fun sharingAppLink(){
        sharingInteractor.shareApp()
    }
    fun openingSupport(){
        sharingInteractor.openSupport()
    }
    fun openingTerms(){
        sharingInteractor.openTerms()
    }
    fun saveSwitch(isTrue: Boolean){
        switchInteractor.saveSwitcher(isTrue)
        switchLiveData.value=isTrue
    }


}