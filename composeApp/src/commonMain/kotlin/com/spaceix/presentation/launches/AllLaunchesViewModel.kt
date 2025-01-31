package com.spaceix.presentation.launches

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spaceix.domain.repository.SpaceXRepository
import kotlinx.coroutines.launch

class AllLaunchesViewModel(
    private val repository: SpaceXRepository
) : ViewModel() {

    fun onBtnClicked() {
        viewModelScope.launch {
            repository.getAllLaunches()
        }
    }
}