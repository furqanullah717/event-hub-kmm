package com.codewithfk.eventhub.event.presentation.details

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.codewithfk.eventhub.di.AppModule
import com.codewithfk.eventhub.event.data.response.EventDetailsResponse
import com.codewithfk.eventhub.event.presentation.home.GoingItem
import com.codewithfk.eventhub.event.presentation.home.HomeViewModel
import com.codewithfk.goodnight.MR
import dev.icerock.moko.mvvm.compose.getViewModel
import dev.icerock.moko.mvvm.compose.viewModelFactory
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import dev.icerock.moko.resources.ImageResource
import dev.icerock.moko.resources.compose.painterResource
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import moe.tlaster.precompose.navigation.Navigator

@Composable
fun EventDetailsScreen(id: String, appModule: AppModule, navigator: Navigator) {
    val viewModel = getViewModel(key = "even_details_screen", factory = viewModelFactory {
        EventDetailsViewModel()
    })

    LaunchedEffect(key1 = true) {
        viewModel.getEventDetails(id)
    }
    val state = viewModel.state.collectAsState()

    state.value?.let {
        Box(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {
            Column(
                modifier = Modifier.fillMaxSize().verticalScroll(
                    rememberScrollState()
                )
            ) {
                EventDetailsContent(it)
            }
            Button(
                onClick = {

                },
                modifier = Modifier.padding(horizontal = 16.dp).height(60.dp).fillMaxWidth()
                    .background(MaterialTheme.colorScheme.background.copy(alpha = 0.2f))
                    .align(Alignment.BottomCenter),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(text = "Buy Ticket Now")
            }

        }

    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun EventDetailsContent(event: EventDetailsResponse) {
    val localDensity = LocalDensity.current
    var columnHeightDp by remember {
        mutableStateOf(0.dp)
    }
    Box(modifier = Modifier) {
        Column(modifier = Modifier.fillMaxSize()) {
            Box(modifier = Modifier.fillMaxWidth().height(244.dp)
                .onGloballyPositioned { coordinates ->
                    // Set column height using the LayoutCoordinates
                    columnHeightDp = with(localDensity) { coordinates.size.height.toDp() + 12.dp }
                }) {
                val pagerState = rememberPagerState(pageCount = {
                    event.images.size
                })
                HorizontalPager(modifier = Modifier.fillMaxSize(), state = pagerState) { page ->
                    KamelImage(
                        resource = asyncPainterResource(event.images[page].url),
                        modifier = Modifier.fillMaxSize(),
                        contentDescription = "Event Image",
                        contentScale = ContentScale.FillBounds
                    )
                }

                Box(modifier = Modifier.fillMaxWidth()) {
                    Row(
                        modifier = Modifier.align(Alignment.CenterStart),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        //image with back button
                        Image(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            modifier = Modifier.padding(16.dp),
                            colorFilter = androidx.compose.ui.graphics.ColorFilter.tint(
                                MaterialTheme.colorScheme.onPrimary
                            )
                        )
                        Spacer(modifier = Modifier.size(8.dp))
                        Text(
                            text = "Event Details",
                            modifier = Modifier.padding(16.dp),
                            fontSize = 20.sp,
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                    Image(
                        painter = painterResource(MR.images.saved),
                        contentDescription = null,
                        modifier = Modifier.size(24.dp).align(Alignment.CenterEnd)
                    )
                }
            }
            Column(modifier = Modifier.fillMaxSize()) {
                Spacer(modifier = Modifier.size(32.dp))
                Text(
                    text = event.name,
                    modifier = Modifier.padding(horizontal = 16.dp),
                    fontSize = 35.sp,
                    lineHeight = 40.sp
                )
                Spacer(modifier = Modifier.size(32.dp))
                EventDetailRow(
                    imageResource = MR.images.ic_date,
                    item1 = event.dates.start.localDate,
                    item2 = event.dates.start.localTime
                )
                Spacer(modifier = Modifier.size(16.dp))
                event._embedded.venues.getOrNull(0)?.let {
                    EventDetailRow(
                        imageResource = MR.images.location,
                        item1 = it.name,
                        item2 = it.address.line1
                    )
                }
                Spacer(modifier = Modifier.size(32.dp))
                event.promoter?.let {
                    EventDetailRow(
                        imageResource = MR.images.promoter, item1 = it.name, item2 = it.description
                    )
                }
                Spacer(modifier = Modifier.size(70.dp))
            }
        }
        // Overlapping View
        Box(
            modifier = Modifier.fillMaxWidth().padding(top = (244 - 32).dp)
                .padding(horizontal = 16.dp)
        ) {
            Card(
                Modifier.clip(RoundedCornerShape(50)).align(Alignment.Center),
            ) {
                Row(modifier = Modifier.padding(vertical = 8.dp)) {
                    Spacer(modifier = Modifier.size(16.dp))
                    GoingItem()
                    Spacer(modifier = Modifier.size(30.dp))
                    Button(onClick = { /*TODO*/ }, shape = RoundedCornerShape(8.dp)) {
                        Text(text = "Invite")
                    }
                    Spacer(modifier = Modifier.size(16.dp))
                }
            }

        }
    }
}

@Composable
fun EventDetailRow(imageResource: ImageResource, item1: String, item2: String) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(imageResource),
            contentDescription = null,
            modifier = Modifier.size(48.dp).clip(RoundedCornerShape(8.dp)),
        )
        Spacer(modifier = Modifier.size(16.dp))
        Column {
            Text(text = item1, fontSize = 16.sp, color = MaterialTheme.colorScheme.onSurface)
            Text(
                text = item2,
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
            )
        }
    }
}