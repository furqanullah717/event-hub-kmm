package com.codewithfk.eventhub.event.data.response

import kotlinx.serialization.Serializable

@Serializable
data class EventDetailsResponse(
    val accessibility: Accessibility,
    val ageRestrictions: AgeRestrictions,
    val dates: Dates,
    val id: String,
    val images: List<Image>,
    val locale: String,
    val name: String,
    val priceRanges: List<PriceRange>,
    val test: Boolean,
    val type: String,
    val url: String,
    val _embedded: EventsLocation,
    val promoter: Promoter
)