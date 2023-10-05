package com.codewithfk.eventhub.event.presentation.home

import com.codewithfk.eventhub.event.data.response.EventListResponse

sealed class HomeScreenState {
    data object Loading : HomeScreenState()
    data class Success(val data: EventListResponse) : HomeScreenState()
    data object Error : HomeScreenState()
}