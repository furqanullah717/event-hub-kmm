package com.codewithfk.eventhub.event


import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import com.codewithfk.eventhub.core.presentation.stringResource
import com.codewithfk.eventhub.di.AppModule
import com.codewithfk.eventhub.event.navigation.NavRouts
import com.codewithfk.eventhub.event.presentation.details.EventDetailsScreen
import com.codewithfk.eventhub.event.presentation.home.HomeScreen
import com.codewithfk.eventhub.event.presentation.splash.SplashScreen
import com.codewithfk.eventhub.theme.AppTheme
import com.codewithfk.goodnight.MR
import dev.icerock.moko.resources.ImageResource
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.NavOptions
import moe.tlaster.precompose.navigation.PopUpTo
import moe.tlaster.precompose.navigation.path
import moe.tlaster.precompose.navigation.rememberNavigator
import moe.tlaster.precompose.navigation.transition.NavTransition

@Composable
fun App(
    darkTheme: Boolean,
    dynamicColor: Boolean,
    appModule: AppModule,
) {
    val navigator = rememberNavigator()
    AppTheme(
        darkTheme,
    ) {
        val home = stringResource(MR.strings.text_home)
        val profile = stringResource(MR.strings.text_profile)
        val showBottomBar = remember { mutableStateOf(false) }
        val selectedTab = remember { mutableStateOf(home) }
        Scaffold(modifier = Modifier.fillMaxSize(), bottomBar = {
            AnimatedVisibility(
                showBottomBar.value, enter = fadeIn(), exit = fadeOut()
            ) {
                BottomAppBar(
                    modifier = Modifier.fillMaxWidth()
                        .background(MaterialTheme.colorScheme.onPrimary)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        BottomNavActionItem(
                            modifier = Modifier.weight(1f).fillMaxSize().clickable {
                                if (selectedTab.value != home) {
                                    navigator.navigate(NavRouts.Home.route)
                                    selectedTab.value = home
                                }
                            },
                            string = home,
                            image = MR.images.ic_explore,
                            selectedTab.value == home
                        )
                        BottomNavActionItem(
                            modifier = Modifier.weight(1f).fillMaxSize().clickable {
                                if (selectedTab.value != profile) {
                                    navigator.navigate(NavRouts.Profile.route)
                                    selectedTab.value = profile
                                }
                            },
                            string = profile,
                            image = MR.images.ic_profile,
                            selectedTab.value == profile
                        )
                    }
                }
            }
        }) {
            NavHost(
                modifier = Modifier.padding(it),
                // Assign the navigator to the NavHost
                navigator = navigator,
                // Navigation transition for the scenes in this NavHost, this is optional
                // The start destination
                initialRoute = NavRouts.Splash.route,
                navTransition = NavTransition(
                    createTransition = slideInHorizontally(
                        initialOffsetX = { it },
                        animationSpec = tween(durationMillis = 300)
                    ),
                    destroyTransition = slideOutHorizontally(
                        targetOffsetX = { it },
                        animationSpec = tween(durationMillis = 300)),
                )
            ) {
                // Define a scene to the navigation graph
                scene(
                    // Scene's route path
                    route = NavRouts.Splash.route,
                    // Navigation transition for this scene, this is optional
                ) {
                    showBottomBar.value = false
                    SplashScreen(
                        modifier = Modifier.fillMaxSize()
                            .background(MaterialTheme.colorScheme.primary),
                        onSplashEndedInvalid = {},
                        onSplashEndedValid = {
                            navigator.navigate(
                                NavRouts.Home.route,
                                NavOptions(popUpTo = PopUpTo(NavRouts.Splash.route, true))
                            )
                        },
                        onStart = {},
                        valid = true
                    )
                }
                scene(
                    // Scene's route path
                    route = NavRouts.Home.route,
                    // Navigation transition for this scene, this is optional

                ) {
                    showBottomBar.value = true
                    HomeScreen(appModule, navigator)
                }
                scene(
                    // Scene's route path
                    route = NavRouts.Profile.route,
                    // Navigation transition for this scene, this is optional

                ) {
                    showBottomBar.value = true
                    Box { }
                }
                scene(
                    // Scene's route path
                    route = NavRouts.EventDetails.route,
                    // Navigation transition for this scene, this is optional
                ) {
                    val id: String = it.path<String>("id")!!
                    showBottomBar.value = false
                    EventDetailsScreen(id, appModule, navigator)

                }
            }
        }
    }
}

@Composable
fun BottomNavActionItem(modifier: Modifier, string: String, image: ImageResource, b: Boolean) {
    val color =
        if (b) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = dev.icerock.moko.resources.compose.painterResource(image),
            contentDescription = string,
            modifier = Modifier,
            colorFilter = ColorFilter.tint(color)
        )
        Text(string, color = color)
    }

}