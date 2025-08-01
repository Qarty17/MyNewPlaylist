package com.example.mynewplaylist.legacy.domain.api

interface ThemeRepository {
    fun getSavedTheme(): Boolean
    fun saveTheme(isDarkTheme: Boolean)
}