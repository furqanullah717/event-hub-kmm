package com.codewithfk.eventhub.event.data.response

import kotlinx.serialization.Serializable

@Serializable
data class EventListResponse(
    val _embedded: Embedded,
)