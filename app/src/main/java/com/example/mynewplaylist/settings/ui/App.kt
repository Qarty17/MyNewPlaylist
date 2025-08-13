package com.example.mynewplaylist.settings.ui


import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.example.mynewplaylist.creator.Creator

class App: Application() {
    private var darkTheme=false
    override fun onCreate() {
        super.onCreate()
        val manager= Creator.provideThemeInteractor(this)
        darkTheme=manager.getSavedTheme()
        if (darkTheme){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }else{
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }

    }
    fun switchTheme(darkThemeEnabled: Boolean){
        val manager=Creator.provideThemeInteractor(this)
        darkTheme=darkThemeEnabled
        AppCompatDelegate.setDefaultNightMode(
            if(darkThemeEnabled){
                manager.saveTheme(darkTheme)
                AppCompatDelegate.MODE_NIGHT_YES
            }else{
                manager.saveTheme(darkTheme)
                AppCompatDelegate.MODE_NIGHT_NO
            }
        )
    }

}