package com.spaceix.presentation.launches

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spaceix.domain.model.LaunchEntity
import com.spaceix.domain.repository.SpaceXRepository
import com.spaceix.domain.unwrap
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AllLaunchesViewModel(
    private val repository: SpaceXRepository
) : ViewModel() {

    private val _launches = MutableStateFlow<List<LaunchEntity>?>(null)
    val launches = _launches.asStateFlow()

    private val _showLoader = MutableStateFlow(false)
    val showLoader = _showLoader.asStateFlow()

    fun onBtnClicked() {
        viewModelScope.launch {
            _showLoader.emit(true)
            repository.getAllLaunches().unwrap(
                onSuccess = {
                    _launches.emit(it.launches)
                },
                onFailure = {
                    //TODO
                }
            )
            _showLoader.emit(false)
        }
    }
}