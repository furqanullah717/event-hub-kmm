package com.codewithfk.eventhub.event.presentation.home

import com.codewithfk.eventhub.event.domain.network.NetworkHandler
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel : ViewModel() {
    private val _state = MutableStateFlow<HomeScreenState>(HomeScreenState.Loading)
    val state: StateFlow<HomeScreenState?> = _state

    init {
        getData()
    }

    fun getData() {
        viewModelScope.launch {
            _state.value = HomeScreenState.Loading
            withContext(Dispatchers.IO) {
                val response = NetworkHandler.createEventsUrl()
                if (response != null) {
                    _state.value = HomeScreenState.Success(response)
                } else {
                    _state.value = HomeScreenState.Error
                }

            }

        }
    }
}