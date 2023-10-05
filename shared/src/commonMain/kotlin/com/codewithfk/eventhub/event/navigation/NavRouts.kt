package com.codewithfk.eventhub.event.navigation

sealed class NavRouts (val route:String) {
    data object Home : NavRouts("/home")
    data object Splash : NavRouts("/splash")
    data object Profile : NavRouts("/profile")
    data object EventDetails : NavRouts("/details/{id}")
}