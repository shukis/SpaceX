package com.spaceix.presentation.rockets.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spaceix.core.navigation.Navigation
import com.spaceix.core.navigation.Route
import com.spaceix.domain.model.RocketEntity
import com.spaceix.domain.unwrap
import com.spaceix.domain.usecase.rockets.DeleteFromFavouriteRocketsUseCase
import com.spaceix.domain.usecase.rockets.GetRocketsUseCase
import com.spaceix.domain.usecase.rockets.MarkAsFavouriteRocketUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RocketsViewModel(
    private val getRocketsUseCase: GetRocketsUseCase,
    private val deleteFromFavouriteRocketsUseCase: DeleteFromFavouriteRocketsUseCase,
    private val markAsFavouriteRocketUseCase: MarkAsFavouriteRocketUseCase
) : ViewModel() {

    private val _rockets = MutableStateFlow<List<RocketEntity>?>(null)
    val rockets = _rockets.asStateFlow()

    private val _navigation = MutableSharedFlow<Navigation>()
    val navigation = _navigation.asSharedFlow()

    private val _showLoader = MutableStateFlow(true)
    val showLoader = _showLoader.asStateFlow()

    init {
        viewModelScope.launch { _showLoader.emit(true) }
    }

    fun onViewResumed() {
        viewModelScope.launch {
            getRockets()
            _showLoader.emit(false)
        }
    }

    private suspend fun getRockets() {
        getRocketsUseCase().unwrap(
            onSuccess = {
                _rockets.emit(it.rockets)
            },
            onFailure = {
                println(it)
            }
        )
    }

    fun onRocketClicked(rocketId: String) {
        viewModelScope.launch { _navigation.emit(Navigation(Route.RocketDetails(rocketId))) }
    }

    fun onSettingsClicked() {
        viewModelScope.launch { _navigation.emit(Navigation(Route.Settings)) }
    }

    fun onFavouriteClicked(rocket: RocketEntity) {
        viewModelScope.launch {
            if (rocket.isFavourite) {
                deleteFromFavouriteRocketsUseCase(DeleteFromFavouriteRocketsUseCase.Arg(rocket.id))
            } else {
                markAsFavouriteRocketUseCase(MarkAsFavouriteRocketUseCase.Arg(rocket.id))
            }
            getRockets()
        }
    }
}