package com.example.mynewplaylist.sharing.domain

import android.content.Intent
import com.example.mynewplaylist.settings.domain.model.EmailData

interface ExternalNavigator {
    fun shareLink(shareAppLink: String): Intent
    fun openLink(termsLink: String): Intent
    fun openEmail(supportEmailData: EmailData):Intent
}