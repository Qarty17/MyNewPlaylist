package com.example.mynewplaylist.domain.api

interface SwitchRepository {
    fun getSavedSwitcher():Boolean
    fun saveSwitcher(isTrue:Boolean)
}