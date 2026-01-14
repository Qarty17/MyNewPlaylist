package com.example.mynewplaylist.sharing.data.impl

import android.content.Context
import android.content.Intent



import com.example.mynewplaylist.settings.domain.model.EmailData
import com.example.mynewplaylist.sharing.domain.ExternalNavigator
import androidx.core.net.toUri

class ExternalNavigatorImpl(private val context:Context): ExternalNavigator {

    override fun shareLink(shareAppLink: String){
        val intent= Intent().apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            action= Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT,shareAppLink)
            type="text/plain"
        }
        context.startActivity(intent)

    }

    override fun openEmail(supportEmailData: EmailData) {
        val subject="Сообщение разработчикам и разработчицам приложения Playlist Maker"
        val message="Спасибо разработчикам и разработчицам за крутое приложение!"
        val supportIntent= Intent(Intent.ACTION_SENDTO).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            data= "mailto:".toUri()
            putExtra(Intent.EXTRA_EMAIL, arrayOf(supportEmailData))
            putExtra(Intent.EXTRA_SUBJECT,subject)
            putExtra(Intent.EXTRA_TEXT,message)

        }
        context.startActivity(supportIntent)

    }
    override fun openLink(termsLink: String){
        val url= termsLink.toUri()
        val intent= Intent(Intent.ACTION_VIEW,url)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)

    }

}