package com.example.mynewplaylist.settings.domain.api

interface ThemeInteractor {
    fun getSavedTheme(): Boolean
    fun saveTheme(isDarkTheme: Boolean)
}