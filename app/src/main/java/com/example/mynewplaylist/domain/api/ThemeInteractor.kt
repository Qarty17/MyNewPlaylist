package com.example.mynewplaylist.domain.api

interface ThemeInteractor {
    fun getSavedTheme(): Boolean
    fun saveTheme(isDarkTheme: Boolean)
}