package com.codewithfk.eventhub.event.presentation.details

import com.codewithfk.eventhub.event.data.response.EventDetailsResponse
import com.codewithfk.eventhub.event.domain.network.NetworkHandler
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EventDetailsViewModel : ViewModel() {

    private val _state = MutableStateFlow<EventDetailsResponse?>(null)
    val state: StateFlow<EventDetailsResponse?> = _state

    fun getEventDetails(id: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val res = NetworkHandler.getEventDetails(id)
               withContext(Dispatchers.Main){
                   _state.tryEmit(res)
               }

            }
        }

    }
}