package com.codewithfk.eventhub.event.presentation.details

import com.codewithfk.eventhub.event.data.response.EventDetailsResponse

sealed class DetailsScreenState {
    data object Loading : DetailsScreenState()
    data class Success(val data: EventDetailsResponse) : DetailsScreenState()
    data object Error : DetailsScreenState()
}