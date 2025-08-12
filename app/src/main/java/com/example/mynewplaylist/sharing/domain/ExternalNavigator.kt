package com.example.mynewplaylist.sharing.domain

import com.example.mynewplaylist.settings.domain.model.EmailData

interface ExternalNavigator {
    fun shareLink(shareAppLink: String)
    fun openLink(termsLink: String)
    fun openEmail(supportEmailData: EmailData)
}