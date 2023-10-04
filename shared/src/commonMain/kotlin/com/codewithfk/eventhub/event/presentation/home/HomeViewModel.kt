package com.codewithfk.eventhub.event.presentation.home

import com.codewithfk.eventhub.event.data.response.EventListResponse
import com.codewithfk.eventhub.event.domain.network.NetworkHandler
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json

class HomeViewModel : ViewModel() {
    private val _state = MutableStateFlow<EventListResponse?>(null)
    val state: StateFlow<EventListResponse?> = _state

    init {
        getData()
    }

    private fun getData() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val response = NetworkHandler.createEventsUrl()
                response.let {
                    _state.value = it
                }
            }

        }
    }
}