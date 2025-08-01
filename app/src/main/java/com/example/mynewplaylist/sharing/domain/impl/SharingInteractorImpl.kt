package com.example.mynewplaylist.sharing.domain.impl

import android.content.Intent
import com.example.mynewplaylist.settings.domain.model.EmailData
import com.example.mynewplaylist.sharing.domain.ExternalNavigator
import com.example.mynewplaylist.sharing.domain.SharingInteractor

class SharingInteractorImpl(private val externalNavigator: ExternalNavigator): SharingInteractor {
    companion object{
        const val APPLINK="https://practicum.yandex.ru/learn/android-developer-plus"
        val EMAIL=EmailData("yourEmail@ya.ru")
        const val TERMSLINK="https://yandex.ru/legal/practicum_offer/"

    }
    override fun shareApp():Intent {
        return externalNavigator.shareLink(getShareAppLink())
    }

    override fun openTerms():Intent {
        return externalNavigator.openLink(getTermsLink())
    }

    override fun openSupport():Intent {
        return externalNavigator.openEmail(getSupportEmailData())
    }
    private fun getShareAppLink(): String{
        return APPLINK
    }
    private fun getTermsLink(): String{
        return TERMSLINK
    }
    private fun getSupportEmailData(): EmailData{
        return EMAIL
    }
}