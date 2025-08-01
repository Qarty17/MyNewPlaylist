package com.example.mynewplaylist.legacy.domain.api

interface SwitchRepository {
    fun getSavedSwitcher():Boolean
    fun saveSwitcher(isTrue:Boolean)
}