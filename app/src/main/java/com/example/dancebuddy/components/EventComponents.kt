package com.example.dancebuddy.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MonotonicFrameClock
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Scale
import com.example.dancebuddy.coredata.Event
import com.example.dancebuddy.coredata.TemplateData
import kotlin.math.absoluteValue

@Composable
fun SingleEventCard(event: Event, index: Int, pagerState: PagerState) {
    val pageOffset = (pagerState.currentPage - index) + pagerState.currentPageOffsetFraction

    Card (
        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(5.dp),
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()
            .graphicsLayer {
                lerp(
                    start = 0.8f.dp,
                    stop = 1f.dp,
                    fraction = 1f - pageOffset.absoluteValue.coerceIn(0f, 1f)
                ).also { scale ->
                    scaleX = scale.value
                    scaleY = scale.value
                }
                alpha = lerp(
                    start = 0.5f.dp,
                    stop = 1f.dp,
                    fraction = 1f - pageOffset.absoluteValue.coerceIn(0f, 1f)
                ).value
            },
    ) {
        event.image?.let {
            AsyncImage(
                modifier = Modifier.fillMaxWidth(),
                model = ImageRequest.Builder(LocalContext.current)
                    .data(it)
                    .crossfade(true)
                    .scale(Scale.FILL)
                    .build(),
                contentDescription = it.toString(),
                contentScale = ContentScale.Crop
            )
        }
        Column (
            modifier = Modifier
                .padding(5.dp)
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = event.title,
                    fontSize = 16.sp,
                    modifier = Modifier.padding(5.dp)
                )
                Text(
                    text = event.date + " " + event.time,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(5.dp)
                )
                //TODO: Implement localization
                //TODO: Implement available places (49/100 reserved)
                //TODO: Implement button for reservation
            }
            HorizontalDivider()
            Text(
                text = event.description,
                fontSize = 10.sp,
                modifier = Modifier.padding(5.dp)
            )
        }
    }
}

@Composable
fun EventCarousel(eventList: List<Event>, innerPadding: PaddingValues) {
    val pagerState = rememberPagerState (initialPage = 0) { eventList.size }

    Column (
        modifier = Modifier.padding(innerPadding),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Swipe!",
            modifier = Modifier
                .padding(top = 20.dp, bottom = 0.dp, start = 15.dp, end = 15.dp),
            fontSize = 22.sp,
            letterSpacing = 2.sp,
            color = MaterialTheme.colorScheme.onBackground,
        )
        HorizontalPager(
            state = pagerState,
            contentPadding = PaddingValues(vertical = 25.dp, horizontal = 60.dp)
        ) { page ->
            SingleEventCard(eventList[page], page, pagerState)
        }
    }
}

@Composable
fun EmptyEvents(innerPadding: PaddingValues) {
    Column(
        modifier = Modifier
            .padding(innerPadding)
            .fillMaxHeight(),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()
                .wrapContentHeight(),
            text = "Sorry :(\nNo events available yet.",
            textAlign = TextAlign.Center,
            fontSize = 24.sp,
            color = Color.LightGray
        )
    }
}