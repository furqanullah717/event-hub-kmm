package com.codewithfk.eventhub.event.data.response

import kotlinx.serialization.Serializable

@Serializable
data class AgeRestrictions(
    val legalAgeEnforced: Boolean
)