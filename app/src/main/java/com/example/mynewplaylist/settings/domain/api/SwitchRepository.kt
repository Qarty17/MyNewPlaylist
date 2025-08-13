package com.example.mynewplaylist.settings.domain.api

interface SwitchRepository {
    fun getSavedSwitcher():Boolean
    fun saveSwitcher(isTrue:Boolean)
}