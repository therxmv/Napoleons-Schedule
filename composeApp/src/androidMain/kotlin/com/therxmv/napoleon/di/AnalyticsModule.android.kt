package com.therxmv.napoleon.di

import com.google.firebase.analytics.FirebaseAnalytics
import com.therxmv.napoleon.data.repository.analytics.AnalyticsRepository
import com.therxmv.napoleon.data.repository.analytics.AnalyticsRepositoryImpl
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

internal actual val analyticsModule = module {
    singleOf(::AnalyticsRepositoryImpl) bind AnalyticsRepository::class

    single { FirebaseAnalytics.getInstance(get()) }
}