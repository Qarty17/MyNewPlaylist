package com.example.mynewplaylist.settings.domain.impl

import com.example.mynewplaylist.settings.domain.api.ThemeInteractor
import com.example.mynewplaylist.settings.domain.api.ThemeRepository

class ThemeInteractorImpl(private val themeRepository: ThemeRepository): ThemeInteractor {

    override fun getSavedTheme(): Boolean {
        return themeRepository.getSavedTheme()
    }

    override fun saveTheme(isDarkTheme: Boolean) {
        themeRepository.saveTheme(isDarkTheme)
    }
}