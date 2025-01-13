package com.example.dancebuddy.screens.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImagePainter
import com.example.dancebuddy.R
import com.example.dancebuddy.Routes
import com.example.dancebuddy.api.AuthPreferences
import com.example.dancebuddy.coredata.user.MainUser
import kotlinx.coroutines.launch

@Composable
fun ProfileCard(profile: MainUser, navController: NavController) {
    val coroutineScope = rememberCoroutineScope()

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
            ProfilePicture(profile.getImage())
            ProfileName(profile)
            Button(
                colors = ButtonDefaults.buttonColors(Color(0xFFB866C4)),
                onClick = {
                    coroutineScope.launch {
                        AuthPreferences.clearTokens()
                        navController.navigate(Routes.LOGIN.name) {
                            popUpTo(navController.graph.startDestinationId) {
                                inclusive = true
                            }
                        }
                    }
                },
            ) {
                Text(
                    text = "Logout",
                    fontSize = 16.sp
                )
            }
        }
    }
}

@Composable
fun ProfilePicture(profileImage: AsyncImagePainter) {
    when (profileImage.state) {
        is AsyncImagePainter.State.Error,
        is AsyncImagePainter.State.Empty -> {
            Image(
                painter = painterResource(id = R.drawable.default_profile_picture),
                contentDescription = "Profile Picture",
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
            )
        }

        is AsyncImagePainter.State.Loading -> {
            CircularProgressIndicator()
        }

        is AsyncImagePainter.State.Success -> {
            Image(
                painter = profileImage,
                contentDescription = "Profile Picture",
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
            )
        }
    }
}

@Composable
fun ProfileName(profile: MainUser) {
    Card (
        modifier = Modifier
            .wrapContentHeight()
            .width(250.dp)
            .wrapContentWidth(Alignment.CenterHorizontally)
            .padding(5.dp)
    ) {
        Column (
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            with(profile) {
                Text(
                    text = username,
                    modifier = Modifier.padding(
                        top = 15.dp,
                        bottom = 0.dp,
                        start = 15.dp,
                        end = 15.dp
                    ),
                    fontSize = 30.sp,
                    color = Color.Black
                )
                Text(
                    text = "$firstName $lastName",
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
                Text(
                    text = "Joined: ${getDateJoined()}",
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
}