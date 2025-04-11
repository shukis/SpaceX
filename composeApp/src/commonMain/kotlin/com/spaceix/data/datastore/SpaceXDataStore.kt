package com.spaceix.data.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
expect class SpaceXDataStore {
    val prefs: DataStore<Preferences>
}
internal const val DATA_STORE_FILE_NAME = "prefs.preferences_pb"