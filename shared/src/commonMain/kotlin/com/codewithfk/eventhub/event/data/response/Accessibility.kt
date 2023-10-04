package com.codewithfk.eventhub.event.data.response

import kotlinx.serialization.Serializable

@Serializable
data class Accessibility(
    val ticketLimit: Int
)