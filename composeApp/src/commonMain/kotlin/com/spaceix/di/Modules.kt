package com.spaceix.di


import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.spaceix.data.core.HttpClientFactory
import com.spaceix.data.database.DatabaseFactory
import com.spaceix.data.database.FavoriteDatabase
import com.spaceix.data.local.LaunchesCache
import com.spaceix.data.local.RocketsCache
import com.spaceix.data.manager.ThemeManagerImpl
import com.spaceix.data.remote.SpaceXApi
import com.spaceix.data.repository.SpaceXRepositoryImpl
import com.spaceix.domain.repository.SpaceXRepository
import com.spaceix.domain.usecase.crew.GetCrewMemberUseCase
import com.spaceix.domain.usecase.launches.DeleteFromFavouriteLaunchesUseCase
import com.spaceix.domain.usecase.launches.GetFavouriteLaunchesUseCase
import com.spaceix.domain.usecase.launches.GetLaunchUseCase
import com.spaceix.domain.usecase.launches.GetLaunchesUseCase
import com.spaceix.domain.usecase.launches.MarkAsFavouriteLaunchUseCase
import com.spaceix.domain.usecase.rockets.DeleteFromFavouriteRocketsUseCase
import com.spaceix.domain.usecase.rockets.GetFavouriteRocketsUseCase
import com.spaceix.domain.usecase.rockets.GetRocketUseCase
import com.spaceix.domain.usecase.rockets.GetRocketsUseCase
import com.spaceix.domain.usecase.rockets.MarkAsFavouriteRocketUseCase
import com.spaceix.manager.ThemeManager
import com.spaceix.presentation.favourites.FavouritesViewModel
import com.spaceix.presentation.launches.details.LaunchDetailsViewModel
import com.spaceix.presentation.launches.list.LaunchesViewModel
import com.spaceix.presentation.rockets.details.RocketDetailsViewModel
import com.spaceix.presentation.rockets.list.RocketsViewModel
import com.spaceix.presentation.root.MainRootViewModel
import com.spaceix.presentation.settings.SettingsViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

expect val platformModule: Module

val sharedModule = module {
    single { HttpClientFactory.create(get()) }
    single { SpaceXApi(get()) }

    singleOf(::SpaceXRepositoryImpl).bind<SpaceXRepository>()
    singleOf(::ThemeManagerImpl).bind<ThemeManager>()
    viewModelOf(::LaunchesViewModel)
    viewModelOf(::MainRootViewModel)
    viewModelOf(::RocketsViewModel)
    viewModelOf(::FavouritesViewModel)
    viewModelOf(::SettingsViewModel)
    viewModel { (launchId: String) -> LaunchDetailsViewModel(get(), get(), get(), get(), launchId) }
    viewModel { (rocketId: String) -> RocketDetailsViewModel(get(), get(), get(), rocketId) }

    single {
        get<DatabaseFactory>().create()
            .setDriver(BundledSQLiteDriver())
            .build()
    }
    single { get<FavoriteDatabase>().favoriteLaunchesDao }
    single { get<FavoriteDatabase>().favoriteRocketsDao }
    singleOf(::LaunchesCache)
    singleOf(::RocketsCache)
}

val useCaseModule = module {
    factoryOf(::GetLaunchesUseCase)
    factoryOf(::GetLaunchUseCase)
    factoryOf(::GetFavouriteLaunchesUseCase)
    factoryOf(::MarkAsFavouriteLaunchUseCase)
    factoryOf(::DeleteFromFavouriteLaunchesUseCase)
    factoryOf(::GetFavouriteRocketsUseCase)
    factoryOf(::MarkAsFavouriteRocketUseCase)
    factoryOf(::DeleteFromFavouriteRocketsUseCase)
    factoryOf(::GetRocketsUseCase)
    factoryOf(::GetRocketUseCase)
    factoryOf(::GetCrewMemberUseCase)
}