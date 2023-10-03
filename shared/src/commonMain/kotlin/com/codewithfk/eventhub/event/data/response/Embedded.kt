package com.codewithfk.eventhub.event.data.response

import kotlinx.serialization.Serializable

@Serializable
data class Embedded(
    val events: List<Event>
)