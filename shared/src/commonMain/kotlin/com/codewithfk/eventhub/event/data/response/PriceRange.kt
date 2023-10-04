package com.codewithfk.eventhub.event.data.response

import kotlinx.serialization.Serializable

@Serializable
data class PriceRange(
    val currency: String,
    val max: Double,
    val min: Double,
    val type: String
)