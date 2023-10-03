package com.codewithfk.eventhub.event.data.response

import kotlinx.serialization.Serializable

@Serializable
data class EmbeddedX(
    val attractions: List<Attraction>?,
    val venues: List<Venue>?
)