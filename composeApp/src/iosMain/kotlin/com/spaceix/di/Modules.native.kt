package com.spaceix.di

import com.spaceix.core.IosUrlOpener
import com.spaceix.core.navigation.UrlOpener
import com.spaceix.data.database.DatabaseFactory
import com.spaceix.data.datastore.SpaceXDataStore
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.darwin.Darwin
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformModule: Module
    get() = module {
        single<HttpClientEngine> { Darwin.create() }
        single { DatabaseFactory() }
        single { SpaceXDataStore() }
        single<UrlOpener> { IosUrlOpener() }
    }