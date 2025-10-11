package com.therxmv.napoleon.di

import org.koin.dsl.module

val appModule = module {
    includes(
        navigationModule,
        dataModule,
        coroutinesModule,
    )
}