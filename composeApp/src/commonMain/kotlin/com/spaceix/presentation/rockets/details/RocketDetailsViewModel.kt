package com.spaceix.presentation.rockets.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spaceix.domain.model.RocketEntity
import com.spaceix.domain.unwrap
import com.spaceix.domain.usecase.rockets.GetRocketUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RocketDetailsViewModel(
    private val getRocketUseCase: GetRocketUseCase,
    private val rocketId: String
) : ViewModel() {

    private val _rocket = MutableStateFlow<RocketEntity?>(null)
    val rocket = _rocket.asStateFlow()

    private val _showImageViewer = MutableStateFlow<Pair<List<String>, Int>?>(null)
    val showImageViewer = _showImageViewer.asStateFlow()

    init {
        viewModelScope.launch {
            getRocketUseCase(GetRocketUseCase.Arg(rocketId)).unwrap(
                onSuccess = { _rocket.emit(it) },
                onFailure = {
                    println(it)
                }
            )
        }
    }

    fun onFavouriteClicked() {

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