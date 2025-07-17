package com.example.mynewplaylist.domain.api

interface SwitchInteractor {
    fun getSavedSwitcher():Boolean
    fun saveSwitcher(isTrue:Boolean)
}