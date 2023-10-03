package com.codewithfk.eventhub.event.data.response

data class PriceRange(
    val currency: String,
    val max: Double,
    val min: Double,
    val type: String
)