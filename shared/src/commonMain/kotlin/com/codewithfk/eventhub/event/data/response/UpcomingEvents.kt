package com.codewithfk.eventhub.event.data.response

data class UpcomingEvents(
    val _filtered: Int,
    val _total: Int,
    val ticketmaster: Int,
    val tmr: Int
)