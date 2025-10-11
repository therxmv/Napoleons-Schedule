package com.therxmv.napoleon

import android.app.Application
import com.therxmv.napoleon.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class NapoleonApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        initKoin()
    }

    private fun initKoin() {
        startKoin {
            androidContext(this@NapoleonApplication)
            androidLogger()
            modules(appModule)
        }
    }
}