package com.example.mynewplaylist.settings.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mynewplaylist.sharing.domain.SharingInteractor

class SettingsViewModel(sharingInteractor: SharingInteractor): ViewModel(){
    private val appLinkLiveData= MutableLiveData<String>()
    fun observeAppLink(): LiveData<String> = appLinkLiveData
    val sharingAppLink=sharingInteractor.shareApp()

}