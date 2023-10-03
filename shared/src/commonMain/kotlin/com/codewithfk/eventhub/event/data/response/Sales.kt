package com.codewithfk.eventhub.event.data.response

data class Sales(
    val presales: List<Presale>,
    val `public`: Public
)