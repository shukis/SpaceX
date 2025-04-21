package com.spaceix.di

import com.spaceix.ThemeViewModel
import com.spaceix.core.IosUrlOpener
import com.spaceix.core.navigation.UrlOpener
import com.spaceix.data.database.DatabaseFactory
import com.spaceix.data.datastore.SpaceXDataStore
import com.spaceix.manager.ThemeManager
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.darwin.Darwin
import kotlinx.coroutines.MainScope
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

actual val platformModule: Module
    get() = module {
        single<HttpClientEngine> { Darwin.create() }
        singleOf(::ThemeViewModel)
        single { DatabaseFactory() }
        single { SpaceXDataStore() }
        single<UrlOpener> { IosUrlOpener() }
        single {
            val themeManager: ThemeManager = get()
            ThemeViewModel(themeManager, MainScope())
        }
    }