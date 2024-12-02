package com.example.dancebuddy.coredata.user

import androidx.compose.runtime.Composable
import coil.compose.rememberAsyncImagePainter
import java.time.Instant
import java.time.LocalDate
import java.util.TimeZone

interface UserProfile {
    val id: Int
    val email: String
    val password: String
    val image: String?
    val dateJoined: Instant
    val firstName: String
    val lastName: String
    val username: String
    val phoneNumber: String?

    @Composable
    fun getImage() = rememberAsyncImagePainter(model = image)

    fun getDateJoined(): LocalDate =
        dateJoined.atZone(TimeZone.getDefault().toZoneId()).toLocalDate()
}