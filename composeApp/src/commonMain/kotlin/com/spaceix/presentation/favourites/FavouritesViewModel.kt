package com.spaceix.presentation.favourites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spaceix.core.navigation.Navigation
import com.spaceix.core.navigation.Route
import com.spaceix.domain.model.LaunchEntity
import com.spaceix.domain.model.RocketEntity
import com.spaceix.domain.unwrap
import com.spaceix.domain.usecase.launches.DeleteFromFavouriteLaunchesUseCase
import com.spaceix.domain.usecase.launches.GetLaunchesUseCase
import com.spaceix.domain.usecase.rockets.DeleteFromFavouriteRocketsUseCase
import com.spaceix.domain.usecase.rockets.GetRocketsUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class FavouritesViewModel(
    private val getLaunchesUseCase: GetLaunchesUseCase,
    private val deleteFromFavouriteLaunchesUseCase: DeleteFromFavouriteLaunchesUseCase,
    private val getRocketsUseCase: GetRocketsUseCase,
    private val deleteFromFavouriteRocketsUseCase: DeleteFromFavouriteRocketsUseCase
) : ViewModel() {

    private val _launches = MutableStateFlow<List<LaunchEntity>?>(null)
    val launches = _launches.asStateFlow()

    private val _rockets = MutableStateFlow<List<RocketEntity>?>(null)
    val rockets = _rockets.asStateFlow()

    private val _navigation = MutableSharedFlow<Navigation>()
    val navigation = _navigation.asSharedFlow()

    private val _showLoader = MutableStateFlow(true)
    val showLoader = _showLoader.asStateFlow()

    init {
        viewModelScope.launch {
            _showLoader.emit(true)
            getLaunches()
            getRockets()
            _showLoader.emit(false)
        }
    }

    private suspend fun getLaunches() {
        getLaunchesUseCase().unwrap(
            onSuccess = {
                _launches.emit(it.launches.filter { launch -> launch.isFavourite })
            },
            onFailure = {
                println(it)
            }
        )
    }

    private suspend fun getRockets() {
        getRocketsUseCase().unwrap(
            onSuccess = {
                _rockets.emit(it.rockets.filter { rocket -> rocket.isFavourite })
            },
            onFailure = {
                println(it)
            }
        )
    }

    fun onLaunchClicked(launchId: String) {
        viewModelScope.launch { _navigation.emit(Navigation(Route.LaunchDetails(launchId))) }
    }

    fun onRocketClicked(rocketId: String) {
        viewModelScope.launch { _navigation.emit(Navigation(Route.RocketDetails(rocketId))) }
    }

    fun onSettingsClicked() {
        viewModelScope.launch { _navigation.emit(Navigation(Route.Settings)) }
    }

    fun onFavouriteLaunchClicked(launch: LaunchEntity) {
        viewModelScope.launch {
            deleteFromFavouriteLaunchesUseCase(DeleteFromFavouriteLaunchesUseCase.Arg(launch.id))
            getLaunches()
        }
    }

    fun onFavouriteRocketClicked(rocket: RocketEntity) {
        viewModelScope.launch {
            deleteFromFavouriteRocketsUseCase(DeleteFromFavouriteRocketsUseCase.Arg(rocket.id))
            getRockets()
        }
    }
}