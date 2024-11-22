package com.example.dancebuddy.coredata

import androidx.compose.ui.graphics.painter.Painter

interface UserProfile {
    val id: Int
    val name: String
    val surname: String
    val email: String
    val password: String
    val image: Painter?
}