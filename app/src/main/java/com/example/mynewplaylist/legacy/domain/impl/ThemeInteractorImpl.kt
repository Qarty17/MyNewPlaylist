package com.example.mynewplaylist.legacy.domain.impl

import com.example.mynewplaylist.legacy.domain.api.ThemeInteractor
import com.example.mynewplaylist.legacy.domain.api.ThemeRepository


class ThemeInteractorImpl(private val themeRepository: ThemeRepository):ThemeInteractor {

    override fun getSavedTheme(): Boolean {
        return themeRepository.getSavedTheme()
    }

    override fun saveTheme(isDarkTheme: Boolean) {
        themeRepository.saveTheme(isDarkTheme)
    }
}