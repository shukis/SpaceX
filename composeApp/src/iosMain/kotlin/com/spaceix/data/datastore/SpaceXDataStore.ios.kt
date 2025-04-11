package com.spaceix.data.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import kotlinx.cinterop.ExperimentalForeignApi
import okio.Path.Companion.toPath
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSUserDomainMask

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class SpaceXDataStore {
    @OptIn(ExperimentalForeignApi::class)
    actual val prefs: DataStore<Preferences> by lazy {
        val directory = NSFileManager.defaultManager.URLForDirectory(
            directory = NSDocumentDirectory,
            inDomain = NSUserDomainMask,
            appropriateForURL = null,
            create = false,
            error = null
        )
        val producePath = requireNotNull(directory).path + "/$DATA_STORE_FILE_NAME"
        PreferenceDataStoreFactory.createWithPath(
            produceFile = { producePath.toPath() }
        )
    }
}