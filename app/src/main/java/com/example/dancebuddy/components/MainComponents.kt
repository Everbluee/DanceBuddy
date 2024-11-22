package com.example.dancebuddy.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.dancebuddy.R
import com.example.dancebuddy.Routes
import com.example.dancebuddy.coredata.viewModels.DanceClassViewModel
import com.example.dancebuddy.coredata.viewModels.EventViewModel
import com.example.dancebuddy.coredata.NotificationData

@Composable
fun Section(title: String, type: String) {
    SectionHeader(title = title)
    Column (
        verticalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        when (type) {
            "classes" -> {
                val danceClassViewModel: DanceClassViewModel = viewModel()
                val danceClasses by danceClassViewModel.data.observeAsState(initial = emptyList())
                val isLoading by danceClassViewModel.loading.observeAsState(initial = false)

                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .size(30.dp)
                    )
                } else if (danceClasses.isEmpty()) {
                    EmptyItem("$type today")
                } else {
                    danceClasses.forEach {
                        ClassItem(it)
                    }
                }

//                ClassItem(TemplateData.getClass())
            }
            "notifications" -> {
                val notificationData = NotificationData.getAllNotifications()

                if (notificationData.isEmpty()) {
                    EmptyItem(type)
                } else {
                    notificationData.forEach{
                        NotificationItem(it)
                    }
                }

                //NotificationItem(TemplateData.getNotification())
            }
            "events" -> {
                val eventViewModel: EventViewModel = viewModel()
                val events by eventViewModel.data.observeAsState(initial = emptyList())
                val isLoading by eventViewModel.loading.observeAsState(initial = false)

                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .size(30.dp)
                    )
                } else if (events.isEmpty()) {
                    EmptyItem("$type this week")
                } else {
                    events.forEach {
                        EventItem(it)
                    }
                }

                //EventItem(TemplateData.getEvent())
            }

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(navController: NavHostController) {
    val currentBackStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry.value?.destination?.route

    CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
        ),
        title = {
            Text(
                "DanceBuddy",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                letterSpacing = 3.sp,
                fontSize = 22.sp,
                color = MaterialTheme.colorScheme.onBackground,
            )
        },
        navigationIcon = {
            IconButton(enabled = false, onClick = { /* TODO */ }) {
                Icon(
                    imageVector = Icons.Filled.Menu,
                    contentDescription = "Back"
                )
            }
        },
        actions = {
            IconButton(onClick = {
                if (currentRoute != "profile") {
                    navController.navigate("profile") {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            }) {
                Icon(
                    imageVector = Icons.Filled.Person,
                    contentDescription = "Profile"
                )
            }
        },
        scrollBehavior = null,
    )
}

@Composable
fun NavBar(navController: NavHostController) {
    val items = listOf(
        NavBarItem(
            route = Routes.CLASSES.name,
            painter = painterResource(id = R.drawable.icon_calendar),
            label = "Classes"
        ),
        NavBarItem(
            route = Routes.HOME.name,
            icon = Icons.Outlined.Home,
            label = "Home"
        ),
        NavBarItem(
            route = Routes.EVENTS.name,
            icon = Icons.Outlined.Notifications,
            label = "Events"
        )
    )

    val defaultIconModifier = Modifier
        .background(
            shape = CircleShape,
            color = MaterialTheme.colorScheme.onPrimary
        )
        .padding(10.dp)
        .size(26.dp)

    val currentBackStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry.value?.destination?.route

    NavigationBar(
        modifier = Modifier.fillMaxWidth(),
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        contentColor = MaterialTheme.colorScheme.onPrimaryContainer
    ) {
        items.forEach { item ->
            NavigationBarItem(
                selected = currentRoute == item.route,
                onClick = {
                    if (currentRoute != item.route) {
                        navController.navigate(item.route) {
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                },
                icon = {
                    if (item.icon != null) {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = item.label,
                            modifier = defaultIconModifier
                        )
                    } else if (item.painter != null) {
                        Icon(
                            painter = item.painter,
                            contentDescription = item.label,
                            modifier = defaultIconModifier
                        )
                    }
                },
                label = {
                    Text(
                        text = item.label,
                        fontSize = 12.sp
                    )
                },
                modifier = Modifier.padding(5.dp)
            )
        }
    }
}

data class NavBarItem(
    val route: String,
    val icon: ImageVector? = null,
    val painter: Painter? = null,
    val label: String
)