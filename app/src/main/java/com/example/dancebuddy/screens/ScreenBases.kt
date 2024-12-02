package com.example.dancebuddy.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.dancebuddy.coredata.user.MainUser
import com.example.dancebuddy.coredata.viewModels.EventViewModel
import com.example.dancebuddy.screens.components.EmptyEvents
import com.example.dancebuddy.screens.components.EventCarousel
import com.example.dancebuddy.screens.components.LoginCard
import com.example.dancebuddy.screens.components.ProfileCard
import com.example.dancebuddy.screens.components.Section

@Composable
fun HomeScreen(innerPadding: PaddingValues, navController: NavHostController) {
    Column(
        modifier = Modifier
            .padding(innerPadding)
            .fillMaxWidth()
            .wrapContentHeight(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Section("Your Classes", "classes")
        Section("Notifications", "notifications")
        Section("Your Events", "events")
    }
}

@Composable
fun ClassScreen(innerPadding: PaddingValues, navController: NavHostController) {
    Column(
        modifier = Modifier
            .padding(innerPadding)
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
    }
}

@Composable
fun EventScreen(innerPadding: PaddingValues, navController: NavHostController) {
    val eventViewModel: EventViewModel = viewModel()
    val events by eventViewModel.data.observeAsState(initial = emptyList())
    val isLoading by eventViewModel.loading.observeAsState(initial = false)

    if (isLoading) {
        Column (
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .size(50.dp)
            )
        }
    } else if (events.isEmpty()) {
        EmptyEvents(innerPadding)
    } else {
        EventCarousel(
            eventList = events,
            innerPadding = innerPadding
        )
    }
}

@Composable
fun ProfileScreen(innerPadding: PaddingValues, navController: NavHostController) {
    Column(
        modifier = Modifier
            .padding(innerPadding)
            .fillMaxWidth()
            .wrapContentHeight(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ProfileCard(MainUser)
    }
}

@Composable
fun LoginScreen(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LoginCard(navController)
    }
}