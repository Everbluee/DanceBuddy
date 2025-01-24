package com.example.dancebuddy.coredata

import com.example.dancebuddy.coredata.user.User
import kotlinx.serialization.Serializable

@Serializable
data class DanceClass(
    val title: String,
    val level: String,
    val description: String = "",
    val time: String,
    val days: List<String>,
    val users: List<User>,
    val instructor: Int //Instructor by ID
)