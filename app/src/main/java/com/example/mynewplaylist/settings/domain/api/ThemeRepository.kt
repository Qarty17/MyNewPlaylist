package com.example.mynewplaylist.settings.domain.api

interface ThemeRepository {
    fun getSavedTheme(): Boolean
    fun saveTheme(isDarkTheme: Boolean)
}