package com.example.mynewplaylist.settings.ui


import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.example.mynewplaylist.common.di.dataModule
import com.example.mynewplaylist.common.di.interactorModule
import com.example.mynewplaylist.common.di.repositoryModule
import com.example.mynewplaylist.common.di.viewModelModule
import com.example.mynewplaylist.settings.domain.api.ThemeInteractor
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App: Application() {
    private var darkTheme=false
    private val themeInteractor: ThemeInteractor by inject()
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(dataModule, interactorModule, repositoryModule, viewModelModule)
        }
        darkTheme=themeInteractor.getSavedTheme()
        if (darkTheme){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }else{
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }

    }
    fun switchTheme(darkThemeEnabled: Boolean){
        darkTheme=darkThemeEnabled
        AppCompatDelegate.setDefaultNightMode(
            if(darkThemeEnabled){
                themeInteractor.saveTheme(darkTheme)
                AppCompatDelegate.MODE_NIGHT_YES
            }else{
                themeInteractor.saveTheme(darkTheme)
                AppCompatDelegate.MODE_NIGHT_NO
            }
        )
    }

}