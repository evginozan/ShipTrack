package com.evginozan.kargotakip

import android.app.Application
import com.evginozan.kargotakip.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

class KargoTakipApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@KargoTakipApplication)
            modules(appModule)
        }
    }
}