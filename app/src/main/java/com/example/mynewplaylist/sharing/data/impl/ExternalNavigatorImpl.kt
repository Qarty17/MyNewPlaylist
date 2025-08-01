package com.example.mynewplaylist.sharing.data.impl

import android.content.Intent
import android.net.Uri

import androidx.core.net.toUri

import com.example.mynewplaylist.settings.domain.model.EmailData
import com.example.mynewplaylist.sharing.domain.ExternalNavigator

class ExternalNavigatorImpl: ExternalNavigator {

    override fun shareLink(shareAppLink: String): Intent {
        val intent= Intent()
        intent.action= Intent.ACTION_SEND
        intent.putExtra(Intent.EXTRA_TEXT,shareAppLink)
        intent.type="text/plain"
        return intent
    }

    override fun openEmail(supportEmailData: EmailData): Intent {
        val subject="Сообщение разработчикам и разработчицам приложения Playlist Maker"
        val message="Спасибо разработчикам и разработчицам за крутое приложение!"
        val supportIntent= Intent(Intent.ACTION_SENDTO)
        supportIntent.data= Uri.parse("mailto:")
        supportIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(supportEmailData))
        supportIntent.putExtra(Intent.EXTRA_SUBJECT,subject)
        supportIntent.putExtra(Intent.EXTRA_TEXT,message)
        return supportIntent
    }
    override fun openLink(termsLink: String): Intent {
        val url= Uri.parse(termsLink)
        return Intent(Intent.ACTION_VIEW, url)
    }

}