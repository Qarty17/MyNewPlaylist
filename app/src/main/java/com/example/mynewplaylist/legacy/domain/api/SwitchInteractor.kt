package com.example.mynewplaylist.legacy.domain.api

interface SwitchInteractor {
    fun getSavedSwitcher():Boolean
    fun saveSwitcher(isTrue:Boolean)
}