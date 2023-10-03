package com.codewithfk.eventhub.event.data.response

data class Page(
    val number: Int,
    val size: Int,
    val totalElements: Int,
    val totalPages: Int
)