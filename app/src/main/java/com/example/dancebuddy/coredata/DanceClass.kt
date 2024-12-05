package com.example.dancebuddy.coredata

import com.example.dancebuddy.coredata.user.User
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DanceClass(
    @SerialName("title") val title: String,
    @SerialName("level") val level: String,
    @SerialName("description") val description: String = "",
    @SerialName("time") val time: String,
    @SerialName("days") val days: List<String>,
    @SerialName("users") val users: List<User>,
    @SerialName("instructor") val instructor: Int //Instructor by ID
)