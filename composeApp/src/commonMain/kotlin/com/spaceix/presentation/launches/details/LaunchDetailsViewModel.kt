package com.spaceix.presentation.launches.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spaceix.domain.getContentOrNull
import com.spaceix.domain.model.CrewMemberEntity
import com.spaceix.domain.model.LaunchEntity
import com.spaceix.domain.unwrap
import com.spaceix.domain.usecase.crew.GetCrewMemberUseCase
import com.spaceix.domain.usecase.launches.DeleteFromFavouriteLaunchesUseCase
import com.spaceix.domain.usecase.launches.GetLaunchUseCase
import com.spaceix.domain.usecase.launches.MarkAsFavouriteLaunchUseCase
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LaunchDetailsViewModel(
    private val getLaunchUseCase: GetLaunchUseCase,
    private val markAsFavouriteLaunchUseCase: MarkAsFavouriteLaunchUseCase,
    private val deleteFromFavouriteLaunchesUseCase: DeleteFromFavouriteLaunchesUseCase,
    private val getCrewMemberUseCase: GetCrewMemberUseCase,
    private val launchId: String
) : ViewModel() {

    private val _launch = MutableStateFlow<LaunchEntity?>(null)
    val launch = _launch.asStateFlow()

    private val _crew = MutableStateFlow<List<CrewMemberEntity>?>(null)
    val crew = _crew.asStateFlow()

    private val _showImageViewer = MutableStateFlow<Pair<List<String>, Int>?>(null)
    val showImageViewer = _showImageViewer.asStateFlow()

    init {
        viewModelScope.launch {
            getLaunch()
            getCrewMembers()
        }
    }

    private suspend fun getLaunch() {
        getLaunchUseCase(GetLaunchUseCase.Arg(launchId)).unwrap(
            onSuccess = { _launch.emit(it) },
            onFailure = {
                println(it)
            }
        )
    }

    private suspend fun getCrewMembers() = coroutineScope {
        _launch.value?.crew?.map {
            async { getCrewMemberUseCase(GetCrewMemberUseCase.Arg(it)) }
        }?.awaitAll()?.mapNotNull { it.getContentOrNull() }?.let {
            _crew.emit(it)
        }
    }

    fun onFavouriteClicked() {
        viewModelScope.launch {
            _launch.value?.let { launch ->
                if (launch.isFavourite) {
                    deleteFromFavouriteLaunchesUseCase(DeleteFromFavouriteLaunchesUseCase.Arg(launch.id))
                } else {
                    markAsFavouriteLaunchUseCase(MarkAsFavouriteLaunchUseCase.Arg(launch.id))
                }
                getLaunch()
            }
        }
    }

    fun onImageClick(index: Int) {
        viewModelScope.launch {
            _showImageViewer.emit(_launch.value?.links?.flickrImages.orEmpty() to index)
        }
    }

    fun onImageViewerDismissed() {
        viewModelScope.launch {
            _showImageViewer.emit(null)
        }
    }
}