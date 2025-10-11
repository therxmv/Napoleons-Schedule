package com.therxmv.napoleon.di

import org.koin.core.context.startKoin

fun initKoin() {
    startKoin {
        modules(appModule)
    }
}