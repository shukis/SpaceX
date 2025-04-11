package com.spaceix.presentation.launches.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spaceix.domain.getContentOrNull
import com.spaceix.domain.model.CrewMemberEntity
import com.spaceix.domain.model.LaunchEntity
import com.spaceix.domain.unwrap
import com.spaceix.domain.usecase.crew.GetCrewMemberUseCase
import com.spaceix.domain.usecase.launches.GetLaunchUseCase
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LaunchDetailsViewModel(
    private val getLaunchUseCase: GetLaunchUseCase,
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
            getLaunchUseCase(GetLaunchUseCase.Arg(launchId)).unwrap(
                onSuccess = { _launch.emit(it) },
                onFailure = {
                    //TODO
                }
            )
            getCrewMembers()
        }
    }

    private suspend fun getCrewMembers() = coroutineScope {
        delay(5000)
        _launch.value?.crew?.map {
            async { getCrewMemberUseCase(GetCrewMemberUseCase.Arg(it)) }
        }?.awaitAll()?.mapNotNull { it.getContentOrNull() }?.let {
            _crew.emit(it)
        }
    }

    fun onFavouriteClicked() {

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