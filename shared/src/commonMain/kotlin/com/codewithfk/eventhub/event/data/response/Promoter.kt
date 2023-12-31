package com.codewithfk.eventhub.event.data.response

import kotlinx.serialization.Serializable

@Serializable
data class Promoter(
    val description: String,
    val id: String,
    val name: String
)