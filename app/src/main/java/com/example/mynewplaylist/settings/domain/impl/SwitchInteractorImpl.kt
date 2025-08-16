package com.example.mynewplaylist.settings.domain.impl

import com.example.mynewplaylist.settings.domain.api.SwitchInteractor
import com.example.mynewplaylist.settings.domain.api.SwitchRepository

class SwitchInteractorImpl(private val switchRepository: SwitchRepository): SwitchInteractor {
    override fun getSavedSwitcher(): Boolean {
        return switchRepository.getSavedSwitcher()
    }

    override fun saveSwitcher(isTrue: Boolean) {
        return switchRepository.saveSwitcher(isTrue)
    }
}