package com.example.dancebuddy.coredata

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DanceClass(
    @SerialName("title") val title: String,
    @SerialName("description") val description: String = "",
    @SerialName("days") val days: String,
    @SerialName("time") val time: String
)