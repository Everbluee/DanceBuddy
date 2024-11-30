package com.example.dancebuddy.coredata

import androidx.compose.ui.graphics.painter.Painter
import java.time.LocalDateTime

interface UserProfile {
    val id: Int
    val email: String
    val password: String
    val image: Painter?
    val dateJoined: LocalDateTime
    val firstName: String
    val lastName: String
    val username: String
    val phoneNumber: String?
}