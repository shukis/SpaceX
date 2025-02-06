package com.spaceix.di


import com.spaceix.data.core.HttpClientFactory
import com.spaceix.data.remote.SpaceXApi
import com.spaceix.data.repository.MockSpaceXRepositoryImpl
import com.spaceix.domain.repository.SpaceXRepository
import com.spaceix.presentation.launches.AllLaunchesViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

expect val platformModule: Module

val sharedModule = module {
    single { HttpClientFactory.create(get()) }
    single { SpaceXApi(get()) }
    singleOf(::MockSpaceXRepositoryImpl).bind<SpaceXRepository>()
    viewModelOf(::AllLaunchesViewModel)
}