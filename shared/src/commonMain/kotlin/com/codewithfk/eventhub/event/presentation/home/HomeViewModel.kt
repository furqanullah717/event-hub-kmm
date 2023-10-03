package com.codewithfk.eventhub.event.presentation.home

import androidx.compose.runtime.mutableStateOf
import com.codewithfk.eventhub.event.data.response.EventListResponse
import com.codewithfk.eventhub.event.domain.network.NetworkHandler
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType.Application.Json
import io.ktor.util.Identity.decode
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
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

    private val json = Json { ignoreUnknownKeys = true }

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