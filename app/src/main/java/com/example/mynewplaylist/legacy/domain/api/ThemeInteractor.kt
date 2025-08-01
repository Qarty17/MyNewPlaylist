package com.example.mynewplaylist.legacy.domain.api

interface ThemeInteractor {
    fun getSavedTheme(): Boolean
    fun saveTheme(isDarkTheme: Boolean)
}