package com.example.dancebuddy.coredata

import com.google.gson.annotations.SerializedName
import java.sql.Date
import java.sql.Time

data class Notification(
    @SerializedName("title") val title: String,
    @SerializedName("description") val description: String,
    @SerializedName("date") val date: String,
    @SerializedName("time") val time: String,
)
