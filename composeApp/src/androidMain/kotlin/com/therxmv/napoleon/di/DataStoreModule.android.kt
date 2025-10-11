package com.therxmv.napoleon.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import com.therxmv.napoleon.data.source.local.datastore.DataStoreSource
import com.therxmv.napoleon.data.source.local.datastore.dataStoreFileName
import okio.Path.Companion.toPath
import org.koin.core.qualifier.named
import org.koin.dsl.module

internal actual val dataStoreModule = module {
    single { createDataStore(get()) }
    single { DataStoreSource(get(), get(named(KoinDispatchers.IO))) }
}

private fun createDataStore(context: Context): DataStore<Preferences> =
    PreferenceDataStoreFactory.createWithPath(
        produceFile = {
            context.filesDir.resolve(dataStoreFileName).absolutePath.toPath()
        },
    )