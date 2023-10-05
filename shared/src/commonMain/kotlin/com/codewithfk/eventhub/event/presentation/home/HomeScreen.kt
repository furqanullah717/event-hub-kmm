package com.codewithfk.eventhub.event.presentation.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.codewithfk.eventhub.di.AppModule
import com.codewithfk.eventhub.event.data.response.Event
import com.codewithfk.eventhub.event.navigation.NavRouteUtils
import com.codewithfk.eventhub.theme.accent1
import com.codewithfk.eventhub.theme.accent2
import com.codewithfk.eventhub.theme.accent3
import com.codewithfk.eventhub.theme.accent4
import com.codewithfk.goodnight.MR
import dev.icerock.moko.mvvm.compose.getViewModel
import dev.icerock.moko.mvvm.compose.viewModelFactory
import dev.icerock.moko.resources.ImageResource
import dev.icerock.moko.resources.compose.painterResource
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import moe.tlaster.precompose.navigation.Navigator

@Composable
fun HomeScreen(appModule: AppModule, navigator: Navigator) {
    val viewModel = getViewModel(key = "home_screen", factory = viewModelFactory {
        HomeViewModel()
    })
    val localDensity = LocalDensity.current
    var columnHeightDp by remember {
        mutableStateOf(0.dp)
    }
    Scaffold(modifier = Modifier.fillMaxSize()) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(Modifier.fillMaxSize()) {
                Column(modifier = Modifier.fillMaxWidth().clip(
                    RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp)
                ).background(MaterialTheme.colorScheme.primary)
                    .padding(horizontal = 16.dp, vertical = 16.dp)
                    .onGloballyPositioned { coordinates ->
                        // Set column height using the LayoutCoordinates
                        columnHeightDp =
                            with(localDensity) { coordinates.size.height.toDp() + 12.dp }
                    }

                ) {
                    Box(modifier = Modifier.fillMaxWidth()) {
                        Image(
                            painter = painterResource(MR.images.ic_menu),
                            contentDescription = null,
                            modifier = Modifier.clip(
                                CircleShape
                            ).clickable { }.align(Alignment.CenterStart).size(48.dp).padding(12.dp),
                            contentScale = ContentScale.Fit
                        )
                        Column(modifier = Modifier.align(Alignment.Center)) {
                            Text(
                                text = "Current Location",
                                color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.8f),
                                fontSize = 12.sp
                            )
                            Text(
                                text = "United States",
                                color = MaterialTheme.colorScheme.onPrimary,
                                fontSize = 14.sp,
                                fontWeight = FontWeight(500)
                            )
                        }
                        Image(
                            painter = painterResource(MR.images.ic_notification),
                            contentDescription = null,
                            modifier = Modifier.clip(
                                CircleShape
                            ).clickable { }.align(Alignment.CenterEnd)
                        )

                    }
                    Spacer(modifier = Modifier.size(24.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Box(modifier = Modifier.weight(5f)) {
                            Row(
                                modifier = Modifier.align(Alignment.CenterStart),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Image(
                                    painter = painterResource(MR.images.ic_search),
                                    contentDescription = null,
                                    modifier = Modifier.clip(
                                        CircleShape
                                    ).clickable { }.size(48.dp).padding(12.dp),
                                    contentScale = ContentScale.Fit
                                )
                                val text = remember { mutableStateOf("") }
                                Column(modifier = Modifier) {
                                    val color =
                                        MaterialTheme.colorScheme.onPrimary.copy(alpha = (0.8f))
                                    TextField(
                                        value = text.value,
                                        onValueChange = { text.value = it },
                                        colors = TextFieldDefaults.colors(
                                            focusedTextColor = color,
                                            unfocusedTextColor = color,
                                            focusedPlaceholderColor = color,
                                            unfocusedPlaceholderColor = color,
                                            disabledContainerColor = Color.Transparent,
                                            errorContainerColor = Color.Transparent,
                                            focusedContainerColor = Color.Transparent,
                                            cursorColor = color,
                                            unfocusedContainerColor = Color.Transparent,
                                            unfocusedIndicatorColor = Color.Transparent,
                                            disabledIndicatorColor = Color.Transparent,
                                            errorIndicatorColor = Color.Transparent,
                                            focusedLabelColor = color,
                                            unfocusedLabelColor = color
                                        ),
                                        label = { Text("Search...") },
                                    )
                                }
                            }
                        }
                        Box(modifier = Modifier.weight(2f)) {
                            Row(
                                Modifier.align(Alignment.CenterEnd).clip(
                                    RoundedCornerShape(32.dp),
                                ).background(MaterialTheme.colorScheme.onPrimary.copy(0.1f))
                                    .padding(horizontal = 16.dp, vertical = 8.dp).clickable { },
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Image(
                                    painter = painterResource(MR.images.ic_filter),
                                    contentDescription = null,
                                    modifier = Modifier.clip(
                                        CircleShape
                                    ).clickable { },
                                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onPrimary)
                                )
                                Spacer(modifier = Modifier.size(8.dp))
                                Text(
                                    text = "Filter",
                                    color = MaterialTheme.colorScheme.onPrimary,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight(500)
                                )
                            }
                        }


                    }
                    Spacer(modifier = Modifier.size(18.dp))
                }

                Spacer(modifier = Modifier.size(32.dp))

                val state = viewModel.state.collectAsState(null)
                when (state.value) {
                    is HomeScreenState.Loading -> {
                        Column(
                            modifier = Modifier.fillMaxSize()
                                .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.2f)),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(48.dp),
                                color = MaterialTheme.colorScheme.primary
                            )
                            Text(text = "Loading...")
                        }
                    }

                    is HomeScreenState.Error -> {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(text = "Error Fetching data! please try again")
                            Button(
                                onClick = { viewModel.getData() },
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(text = "Retry")
                            }
                        }
                    }

                    is HomeScreenState.Success -> {
                        state.value?.let {
                            val response = (it as HomeScreenState.Success).data
                            EventRow("Popular Events", response._embedded.events, navigator)
                        }
                    }

                    else -> {
                        Text(text = "Null")
                    }
                }

            }

            Row(
                modifier = Modifier.fillMaxWidth().padding(top = columnHeightDp)
                    .horizontalScroll(rememberScrollState()),
            ) {
                //parse color hex
                Spacer(modifier = Modifier.size(20.dp))
                Category("Sports", MR.images.ic_sports, accent1)
                Category("Music", MR.images.ic_music, accent2)
                Category("Food", MR.images.ic_food, accent3)
                Category("Game", MR.images.game, accent4)
                Category("Art", MR.images.art, accent1)
                Spacer(modifier = Modifier.size(20.dp))
            }

        }
    }
}

@Composable
fun Category(title: String, image: ImageResource, backGroundColor: Color) {
    Spacer(modifier = Modifier.size(4.dp))
    Row(
        modifier = Modifier.clip(RoundedCornerShape(20.dp)).background(backGroundColor)
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Image(
            painterResource(image),
            contentDescription = null,
            modifier = Modifier.size(18.dp),
            contentScale = ContentScale.FillBounds,
            colorFilter = ColorFilter.tint(Color.White)
        )
        Spacer(modifier = Modifier.size(8.dp))
        Text(title, color = Color.White)
    }
    Spacer(modifier = Modifier.size(4.dp))
}

@Composable
fun EventRow(title: String, events: List<Event>, navigator: Navigator) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Box(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)) {
            Text(
                title,
                fontSize = 18.sp,
                fontWeight = FontWeight(600),
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.align(Alignment.CenterStart)
            )

            Text(
                "View All",
                fontSize = 18.sp,
                fontWeight = FontWeight(400),
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f),
                modifier = Modifier.align(Alignment.CenterEnd)
            )

        }
        LazyRow {
            items(events.size) { index ->
                EventCard(events[index], onClicked = {
                    navigator.navigate(NavRouteUtils.getEventDetailsRoute(it))
                })
            }
        }
    }
}

@Composable
fun EventCard(event: Event, onClicked: (String) -> Unit) {
    ElevatedCard(
        modifier = Modifier.clip(RoundedCornerShape(16.dp)).padding(8.dp)
            .clickable { onClicked(event.id!!) },
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
    ) {
        Column(modifier = Modifier.width(237.dp).padding(8.dp)) {
            Box(modifier = Modifier.fillMaxWidth()) {
                KamelImage(
                    resource = asyncPainterResource(data = event.images?.find { it.ratio == "16_9" }?.url!!),
                    contentDescription = null,
                    contentScale = ContentScale.FillWidth,
                    modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(16.dp))
                        .aspectRatio(1.7f, matchHeightConstraintsFirst = true),
                )
                Column(
                    modifier = Modifier
                        .padding(20.dp)
                        .background(MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.8f))
                        .clip(RoundedCornerShape(16.dp)).align(Alignment.TopStart),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "10", fontSize = 16.sp, color = MaterialTheme.colorScheme.error)
                    Text(text = "NOV", fontSize = 14.sp, color = MaterialTheme.colorScheme.error)
                }
                Image(
                    painter = painterResource(MR.images.saved),
                    contentDescription = null,
                    modifier = Modifier.align(Alignment.TopEnd).padding(16.dp)
                )
            }
            Spacer(modifier = Modifier.size(16.dp))
            Text(
                text = event.name ?: "",
                fontSize = 16.sp,
                fontWeight = FontWeight(600),
                color = MaterialTheme.colorScheme.onBackground,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.size(8.dp))
            GoingItem()

            Spacer(Modifier.size(16.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    modifier = Modifier.size(18.dp),
                    painter = painterResource(MR.images.pin),
                    contentDescription = null,
                )
                Spacer(Modifier.size(8.dp))
                Text(
                    text = event._embedded?.venues?.getOrNull(0)?.address?.line1 ?: "",
                    fontSize = 16.sp,
                    fontWeight = FontWeight(500),
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            Spacer(Modifier.size(16.dp))
        }
    }
}


@Composable
fun GoingItem() {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box() {
            Row {
                Spacer(Modifier.size(48.dp))
                Image(
                    modifier = Modifier.size(48.dp),
                    painter = painterResource(MR.images.ic_image2),
                    contentDescription = null,
                )
            }
            Row {
                Spacer(Modifier.size(24.dp))
                Image(
                    modifier = Modifier.size(48.dp),
                    painter = painterResource(MR.images.ic_image1),
                    contentDescription = null,

                    )
            }
            Image(
                modifier = Modifier.size(48.dp).clip(CircleShape),
                painter = painterResource(MR.images.ic_image),
                contentDescription = null,
            )
        }
        Text(
            text = " 20+ Going",
            fontSize = 14.sp,
            fontWeight = FontWeight(500),
            color = MaterialTheme.colorScheme.primary
        )
    }
}