package com.example.dancebuddy.screens.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
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
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
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
import com.example.dancebuddy.coredata.NotificationData
import com.example.dancebuddy.coredata.viewModels.DanceClassViewModel
import com.example.dancebuddy.coredata.viewModels.EventViewModel
import java.time.LocalDate

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
                val isLoading by danceClassViewModel.loading.observeAsState(initial = false)

                when (isLoading) {
                    true -> {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                                .size(30.dp)
                        )
                    }

                    false -> {
                        val danceClasses = danceClassViewModel.getUsersClasses().filter {
                            it.days.contains(LocalDate.now().dayOfWeek.name.lowercase())
                        }

                        if (danceClasses.isEmpty()) {
                            EmptyItem("$type today")
                        } else {
                            danceClasses.map {
                                ClassItem(it)
                            }
                        }
                    }
                }
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
        modifier = Modifier.background(
            brush = Brush.linearGradient(
                listOf(
                    Color(0xFF7C34C1),
                    Color(0xFFE88EC9)
                )
            )
        ),
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = Color.Transparent,
            titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
        ),
        title = {
            Text(
                "DanceBuddy",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                letterSpacing = 3.sp,
                fontSize = 22.sp,
                color = Color.White
            )
        },
        navigationIcon = {
            IconButton(
                enabled = false,
                onClick = { /* TODO */ },
                colors = IconButtonDefaults.iconButtonColors(
                    contentColor = Color.White,
                )
            ) {
                Icon(
                    imageVector = Icons.Filled.Menu,
                    contentDescription = "Back"
                )
            }
        },
        actions = {
            IconButton(
                onClick = {
                    if (currentRoute != "profile") {
                        navController.navigate("profile") {
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                },
                colors = IconButtonDefaults.iconButtonColors(
                    contentColor = Color.White,
                )
            ) {
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

    val currentBackStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry.value?.destination?.route

    NavigationBar(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                brush = Brush.linearGradient(
                    listOf(
                        Color(0xFF7C34C1),
                        Color(0xFFE88EC9)
                    )
                )
            ),
        containerColor = Color.Transparent,
        contentColor = Color.White
    ) {
        items.forEach { item ->
            val isSelected = currentRoute == item.route
            NavigationBarItem(
                selected = isSelected,
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
                            tint = if (isSelected) Color.White else Color(0xFFDED1E2),
                            modifier = Modifier.size(32.dp)
                        )
                    } else if (item.painter != null) {
                        Icon(
                            painter = item.painter,
                            contentDescription = item.label,
                            tint = if (isSelected) Color.White else Color(0xFFDED1E2),
                            modifier = Modifier.size(32.dp)
                        )
                    }
                },
                label = {
                    Text(
                        text = item.label,
                        fontSize = 12.sp,
                        color = if (isSelected) Color.White else Color(0xFFDED1E2)
                    )
                },
                modifier = Modifier.padding(5.dp),
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color.White,
                    unselectedIconColor = Color(0xFFDED1E2),
                    selectedTextColor = Color.White,
                    unselectedTextColor = Color(0xFFDED1E2),
                    indicatorColor = Color.Transparent
                )
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