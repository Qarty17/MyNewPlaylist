package com.example.mynewplaylist.domain.api

interface ThemeRepository {
    fun getSavedTheme(): Boolean
    fun saveTheme(isDarkTheme: Boolean)
}