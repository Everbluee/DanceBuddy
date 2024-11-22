package com.example.dancebuddy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.dancebuddy.components.NavBar
import com.example.dancebuddy.components.TopBar
import com.example.dancebuddy.screens.ClassScreen
import com.example.dancebuddy.screens.EventScreen
import com.example.dancebuddy.screens.HomeScreen
import com.example.dancebuddy.screens.ProfileScreen
import com.example.dancebuddy.ui.theme.DanceBuddyTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DanceBuddyTheme {
                App()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App() {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    val navController = rememberNavController()

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopBar(navController)
        },
        bottomBar = {
            NavBar(navController)
        }
    ) { innerPadding ->
        NavHost(navController = navController, startDestination = Routes.HOME.name) {
            composable(route = Routes.HOME.name,
                ) { HomeScreen(innerPadding, navController) }
            composable(route = Routes.CLASSES.name,
                ) { ClassScreen(innerPadding, navController) }
            composable(route = Routes.EVENTS.name,
                ) { EventScreen(innerPadding, navController) }
            composable(
                route = Routes.PROFILE.name,
                enterTransition = {
                    fadeIn(
                        animationSpec = tween(300, easing = EaseIn)
                    ) + slideInHorizontally(
                        animationSpec = tween(500)
                    ) { fullWidth -> fullWidth/3 }
                },
                exitTransition = {
                    fadeOut(
                        animationSpec = tween(300, easing = EaseOut)
                    ) + slideOutHorizontally(
                        animationSpec = tween(500)
                    ) { 200 }
                },
            ) {
                ProfileScreen(innerPadding, navController)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AppPreview() {
    DanceBuddyTheme {
        App()
    }
}