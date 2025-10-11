package com.therxmv.napoleon.di

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import org.koin.core.qualifier.named
import org.koin.dsl.module

val coroutinesModule = module {
    single<CoroutineDispatcher>(named(KoinDispatchers.IO)) { Dispatchers.IO }
    single<CoroutineDispatcher>(named(KoinDispatchers.Main)) { Dispatchers.Main }
    single<CoroutineDispatcher>(named(KoinDispatchers.Default)) { Dispatchers.Default }
}

enum class KoinDispatchers {
    Main,
    Default,
    IO,
}