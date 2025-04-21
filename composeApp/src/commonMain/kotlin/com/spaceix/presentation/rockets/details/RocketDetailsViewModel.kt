package com.spaceix.presentation.rockets.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spaceix.domain.model.RocketEntity
import com.spaceix.domain.unwrap
import com.spaceix.domain.usecase.rockets.DeleteFromFavouriteRocketsUseCase
import com.spaceix.domain.usecase.rockets.GetRocketUseCase
import com.spaceix.domain.usecase.rockets.MarkAsFavouriteRocketUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RocketDetailsViewModel(
    private val getRocketUseCase: GetRocketUseCase,
    private val deleteFromFavouriteRocketsUseCase: DeleteFromFavouriteRocketsUseCase,
    private val markAsFavouriteRocketUseCase: MarkAsFavouriteRocketUseCase,
    private val rocketId: String
) : ViewModel() {

    private val _rocket = MutableStateFlow<RocketEntity?>(null)
    val rocket = _rocket.asStateFlow()

    private val _showImageViewer = MutableStateFlow<Pair<List<String>, Int>?>(null)
    val showImageViewer = _showImageViewer.asStateFlow()

    init {
        viewModelScope.launch { getRocket() }
    }

    private suspend fun getRocket() {
        getRocketUseCase(GetRocketUseCase.Arg(rocketId)).unwrap(
            onSuccess = { _rocket.emit(it) },
            onFailure = {
                println(it)
            }
        )
    }

    fun onFavouriteClicked() {
        viewModelScope.launch {
            _rocket.value?.let { rocket ->
                if (rocket.isFavourite) {
                    deleteFromFavouriteRocketsUseCase(DeleteFromFavouriteRocketsUseCase.Arg(rocket.id))
                } else {
                    markAsFavouriteRocketUseCase(MarkAsFavouriteRocketUseCase.Arg(rocket.id))
                }
                getRocket()
            }
        }
    }

    fun onImageClick(index: Int) {
        viewModelScope.launch {
            _showImageViewer.emit(_rocket.value?.flickrImages?.filterNotNull().orEmpty() to index)
        }
    }

    fun onImageViewerDismissed() {
        viewModelScope.launch {
            _showImageViewer.emit(null)
        }
    }
}