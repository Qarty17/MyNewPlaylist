package com.example.mynewplaylist.settings.domain.api

interface SwitchInteractor {
    fun getSavedSwitcher():Boolean
    fun saveSwitcher(isTrue:Boolean)
}