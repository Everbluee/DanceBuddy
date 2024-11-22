package com.example.dancebuddy.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dancebuddy.coredata.DanceClass
import com.example.dancebuddy.coredata.Event
import com.example.dancebuddy.coredata.Notification

@Composable
fun SectionHeader(title: String) {
    Column(
        modifier = Modifier
            .padding(top = 10.dp, start = 10.dp, end = 10.dp),
    ) {
        HorizontalDivider(
            color = MaterialTheme.colorScheme.outline
        )
        Text(
            text = title,
            modifier = Modifier
                .padding(horizontal = 5.dp)
                .fillMaxWidth()
                .wrapContentHeight(),
            textAlign = TextAlign.Center,
            letterSpacing = 2.sp,
            fontSize = 15.sp,
        )
    }
}

@Composable
fun ClassItem(danceClass: DanceClass) {
    Card(
        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(5.dp),
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        Column(
            modifier = Modifier
                .padding(5.dp)
                .fillMaxWidth()
                .wrapContentHeight(),
        ) {
            Row {
                Text(
                    text = danceClass.time.substring(0, 5),
                    modifier = Modifier.padding(5.dp)
                )
            }
            HorizontalDivider()
            Row {
                Text(
                    text = danceClass.title,
                    modifier = Modifier.padding(5.dp)
                )
            }
        }
    }
}

@Composable
fun EventItem(event: Event) {
    Card(
        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(5.dp),
        modifier = Modifier
            .padding(10.dp)
            .wrapContentHeight()
            .fillMaxWidth()
    ) {
        event.image?.let {
            Image(
                painter = it,
                contentDescription = it.toString(),
                modifier = Modifier
                    .height(150.dp)
                    .fillMaxWidth()
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
fun NotificationItem(notification: Notification) {
    Card(
        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(5.dp),
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
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
                    text = notification.title,
                    fontSize = 16.sp,
                    modifier = Modifier.padding(5.dp)
                )
                Text(
                    text = notification.date + " " + notification.time,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(5.dp)
                )
                //TODO: Implement localization
            }
            HorizontalDivider()
            Text(
                text = notification.description,
                fontSize = 10.sp,
                modifier = Modifier.padding(5.dp)
            )
        }
    }
}

@Composable
fun EmptyItem(type: String) {
    Text(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
            .wrapContentHeight(),
        text = "No $type.",
        textAlign = TextAlign.Center,
        fontSize = 12.sp,
        color = Color.LightGray
    )
}