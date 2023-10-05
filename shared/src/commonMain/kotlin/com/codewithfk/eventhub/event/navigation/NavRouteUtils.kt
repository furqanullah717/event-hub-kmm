package com.codewithfk.eventhub.event.navigation

object NavRouteUtils {
    fun getEventDetailsRoute(id: String): String {
        return NavRouts.EventDetails.route.replace("{id}", id)
    }
}