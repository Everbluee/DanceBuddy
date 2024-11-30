package com.example.dancebuddy.screens.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dancebuddy.R
import com.example.dancebuddy.coredata.DancerProfile
import com.example.dancebuddy.coredata.TemplateData

@Composable
fun ProfileCard(profile: DancerProfile) {
    Card (
        modifier = Modifier
            .wrapContentHeight()
            .wrapContentWidth()
            .padding(15.dp),
        shape = RoundedCornerShape(10.dp)
    ) {
        Column (
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(15.dp)
        ) {
            ProfilePicture(profile.image)
            ProfileName(name = profile.firstName, surname = profile.lastName, email = profile.email)
        }
    }
}

@Composable
fun ProfilePicture(profileImage: Painter?) {
    val image = profileImage ?: painterResource(id = R.drawable.default_profile_picture)

    Image(
        painter = image,
        contentDescription = "Profile Picture",
        modifier = Modifier
            .size(100.dp)
            .clip(CircleShape)
    )
}

@Composable
fun ProfileName(name: String, surname: String, email: String) {
    Card (
        modifier = Modifier
            .wrapContentHeight()
            .wrapContentWidth()
            .padding(5.dp)
    ) {
        Column (
            modifier = Modifier
                .wrapContentHeight()
                .wrapContentWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "$name $surname",
                modifier = Modifier.padding(
                    top = 15.dp,
                    bottom = 0.dp,
                    start = 15.dp,
                    end = 15.dp
                ),
                fontSize = 24.sp
            )
            Text(
                text = email,
                modifier = Modifier.padding(
                    top = 10.dp,
                    bottom = 0.dp,
                    start = 15.dp,
                    end = 15.dp
                ),
                fontSize = 12.sp
            )
        }
    }
}

@Composable
@Preview
fun Prev() {
    ProfileCard(TemplateData.getProfile())
}
