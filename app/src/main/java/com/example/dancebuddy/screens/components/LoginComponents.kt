package com.example.dancebuddy.screens.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush.Companion.linearGradient
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.dancebuddy.Routes
import com.example.dancebuddy.api.AuthPreferences
import com.example.dancebuddy.api.LoginRequest
import com.example.dancebuddy.api.RetrofitClient
import com.example.dancebuddy.coredata.user.MainUser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun LoginCard(navController: NavHostController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(cardBackground()),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .padding(start = 30.dp, end = 30.dp, bottom = 70.dp, top = 50.dp)
                .fillMaxWidth()
                .wrapContentHeight(),
            shape = RoundedCornerShape(60.dp),
            elevation = CardDefaults.cardElevation(12.dp),
            colors = CardDefaults.cardColors(Color.White)
        ) {
            Card(
                modifier = Modifier
                    .padding(24.dp)
                    .fillMaxWidth()
                    .wrapContentHeight(),
                shape = RoundedCornerShape(36.dp),
                colors = CardDefaults.cardColors(Color.White)
            ) {
                CardContent(navController)
            }
        }
    }
}

@Composable
private fun CardContent(navController: NavHostController) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }

    CardTopBox()

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .wrapContentHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Username") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            singleLine = true
        )

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp),
            visualTransformation = PasswordVisualTransformation(),
            singleLine = true
        )

        Button(
            colors = ButtonDefaults.buttonColors(Color(0xFFB866C4)),
            onClick = {
                val loginRequest = LoginRequest(username, password)

                isLoading = true
                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        val response = RetrofitClient.apiService.login(loginRequest)

                        val userID = with(AuthPreferences) {
                            saveAccessToken(response.access)
                            saveRefreshToken(response.refresh)

                            val token = decodeToken()
                            token?.let { getUserIdFromToken(it) }
                        }

                        withContext(Dispatchers.IO) {
                            userID?.let {
                                MainUser.fetchUserData(userID)
                            }

                            withContext(Dispatchers.Main) {
                                navController.navigate(Routes.HOME.name) {
                                    popUpTo(Routes.LOGIN.name) { inclusive = true }
                                }
                            }
                        }
                    } catch (e: Exception) {
                        println("Login failed: ${e.message}")
                    } finally {
                        isLoading = false
                    }
                }
            },
            enabled = !isLoading
        ) {
            if (isLoading) CircularProgressIndicator() else Text(
                text = "Login",
                fontSize = 16.sp
            )
        }
    }
}

@Composable
private fun CardTopBox() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(cardBackground())
    ) {
        Column(
            modifier = Modifier
                .padding(24.dp)
                .fillMaxWidth()
                .wrapContentHeight(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "DanceBuddy",
                textAlign = TextAlign.Center,
                fontSize = 32.sp,
                color = Color.White
            )
            Text(
                text = "Please log in to continue.",
                textAlign = TextAlign.Center,
                fontSize = 18.sp,
                color = Color.White
            )
        }
    }
}

@Composable
private fun cardBackground() = linearGradient(
    colors = listOf(
        Color(0xFF7C34C1),
        Color(0xFFE88EC9)
    )
)