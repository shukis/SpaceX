package com.spaceix.presentation.launches.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spaceix.core.navigation.Navigation
import com.spaceix.core.navigation.Route
import com.spaceix.domain.model.LaunchEntity
import com.spaceix.domain.unwrap
import com.spaceix.domain.usecase.launches.DeleteFromFavouriteLaunchesUseCase
import com.spaceix.domain.usecase.launches.GetLaunchesUseCase
import com.spaceix.domain.usecase.launches.MarkAsFavouriteLaunchUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LaunchesViewModel(
    private val getLaunchesUseCase: GetLaunchesUseCase,
    private val markAsFavouriteLaunchUseCase: MarkAsFavouriteLaunchUseCase,
    private val deleteFromFavouriteLaunchesUseCase: DeleteFromFavouriteLaunchesUseCase
) : ViewModel() {

    private val _launches = MutableStateFlow<List<LaunchEntity>?>(null)
    val launches = _launches.asStateFlow()

    private val _navigation = MutableSharedFlow<Navigation>()
    val navigation = _navigation.asSharedFlow()

    private val _showLoader = MutableStateFlow(true)
    val showLoader = _showLoader.asStateFlow()

    init {
        viewModelScope.launch { _showLoader.emit(true) }
    }

    fun onViewResumed() {
            viewModelScope.launch {
                getLaunches()
                _showLoader.emit(false)
            }
    }

    private suspend fun getLaunches() {
        getLaunchesUseCase().unwrap(
            onSuccess = {
                _launches.emit(it.launches)
            },
            onFailure = {
                println(it)
            }
        )
    }

    fun onLaunchClicked(launchId: String) {
        viewModelScope.launch { _navigation.emit(Navigation(Route.LaunchDetails(launchId))) }
    }

    fun onSettingsClicked() {
        viewModelScope.launch { _navigation.emit(Navigation(Route.Settings)) }
    }

    fun onFavouriteClicked(launch: LaunchEntity) {
        viewModelScope.launch {
            if (launch.isFavourite) {
                deleteFromFavouriteLaunchesUseCase(DeleteFromFavouriteLaunchesUseCase.Arg(launch.id))
            } else {
                markAsFavouriteLaunchUseCase(MarkAsFavouriteLaunchUseCase.Arg(launch.id))
            }
            getLaunches()
        }
    }
}