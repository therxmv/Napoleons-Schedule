package com.therxmv.napoleon.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import com.therxmv.napoleon.data.source.local.datastore.DataStoreSource
import com.therxmv.napoleon.data.source.local.datastore.dataStoreFileName
import kotlinx.cinterop.ExperimentalForeignApi
import okio.Path.Companion.toPath
import org.koin.core.qualifier.named
import org.koin.dsl.module
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSUserDomainMask

internal actual val dataStoreModule = module {
    single { createDataStore() }
    single { DataStoreSource(get(), get(named(KoinDispatchers.IO))) }
}

@OptIn(ExperimentalForeignApi::class)
private fun createDataStore(): DataStore<Preferences> =
    PreferenceDataStoreFactory.createWithPath(
        produceFile = {
            val documentPath = NSFileManager.defaultManager.URLForDirectory(
                directory = NSDocumentDirectory,
                inDomain = NSUserDomainMask,
                appropriateForURL = null,
                create = false,
                error = null,
            )?.path
            (requireNotNull(documentPath) + "/$dataStoreFileName").toPath()
        },
    )