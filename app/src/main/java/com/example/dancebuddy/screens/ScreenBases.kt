package com.example.dancebuddy.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.dancebuddy.coredata.DanceClass
import com.example.dancebuddy.coredata.user.MainUser
import com.example.dancebuddy.coredata.viewModels.DanceClassViewModel
import com.example.dancebuddy.coredata.viewModels.EventViewModel
import com.example.dancebuddy.screens.components.DanceClassModal
import com.example.dancebuddy.screens.components.DanceClassSection
import com.example.dancebuddy.screens.components.EmptyEvents
import com.example.dancebuddy.screens.components.EventCarousel
import com.example.dancebuddy.screens.components.LoginCard
import com.example.dancebuddy.screens.components.ProfileCard
import com.example.dancebuddy.screens.components.Section
import com.example.dancebuddy.screens.components.SectionHeader

@Composable
fun HomeScreen(innerPadding: PaddingValues, navController: NavHostController) {
    Column(
        modifier = Modifier
            .padding(innerPadding)
            .fillMaxWidth()
            .wrapContentHeight(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Section("Your Classes Today", "classes")
        Section("Notifications", "notifications")
        Section("Your Events This Week", "events")
    }
}

@Composable
fun ClassScreen(innerPadding: PaddingValues, navController: NavHostController) {
    val danceClassViewModel: DanceClassViewModel = viewModel()
    val danceClasses by danceClassViewModel.data.observeAsState(initial = emptyList())
    val filteredClasses = danceClasses.filter { danceClass ->
        danceClass.users.any { user -> user.id == MainUser.id }
    }
    val isLoading by danceClassViewModel.loading.observeAsState(initial = false)
    var selectedDanceClass by remember { mutableStateOf<DanceClass?>(null) }

    if (isLoading) {
        Column(
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
    } else if (danceClasses.isEmpty()) {
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                color = Color.Gray,
                text = "No classes."
            )
        }
    } else {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            item {
                SectionHeader("Your classes")
            }
            item {
                DanceClassSection(
                    innerPadding = PaddingValues(16.dp),
                    danceClasses = filteredClasses,
                    onClassClick = { selectedDanceClass = it }
                )
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
            }

            item {
                SectionHeader("All classes")
            }
            item {
                DanceClassSection(
                    innerPadding = PaddingValues(16.dp),
                    danceClasses = danceClasses,
                    onClassClick = { selectedDanceClass = it }
                )
            }

            item {
                selectedDanceClass?.let { danceClass ->
                    DanceClassModal(
                        danceClass = danceClass,
                        onDismiss = { selectedDanceClass = null }
                    )
                }
            }
        }

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
        ProfileCard(MainUser, navController)
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