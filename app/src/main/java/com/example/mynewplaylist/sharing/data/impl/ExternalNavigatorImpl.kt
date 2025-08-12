package com.example.mynewplaylist.sharing.data.impl

import android.content.Context
import android.content.Intent
import android.net.Uri


import com.example.mynewplaylist.settings.domain.model.EmailData
import com.example.mynewplaylist.sharing.domain.ExternalNavigator
import androidx.core.net.toUri

class ExternalNavigatorImpl(context:Context): ExternalNavigator {
    val myContext=context
    override fun shareLink(shareAppLink: String){
        val intent= Intent()
        intent.action= Intent.ACTION_SEND
        intent.putExtra(Intent.EXTRA_TEXT,shareAppLink)
        intent.type="text/plain"
        myContext.startActivity(intent)

    }

    override fun openEmail(supportEmailData: EmailData) {
        val subject="Сообщение разработчикам и разработчицам приложения Playlist Maker"
        val message="Спасибо разработчикам и разработчицам за крутое приложение!"
        val supportIntent= Intent(Intent.ACTION_SENDTO)
        supportIntent.data= "mailto:".toUri()
        supportIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(supportEmailData))
        supportIntent.putExtra(Intent.EXTRA_SUBJECT,subject)
        supportIntent.putExtra(Intent.EXTRA_TEXT,message)
        myContext.startActivity(supportIntent)

    }
    override fun openLink(termsLink: String){
        val url= termsLink.toUri()
        val intent= Intent(Intent.ACTION_VIEW,url)
        myContext.startActivity(intent)

    }

}