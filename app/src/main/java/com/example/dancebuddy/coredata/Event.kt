package com.example.dancebuddy.coredata

import androidx.compose.ui.graphics.painter.Painter
import com.google.gson.annotations.SerializedName
import java.sql.Time
import java.sql.Date

data class Event(
    @SerializedName("title") val title: String,
    @SerializedName("description") val description: String,
    @SerializedName("date") val date: String,
    @SerializedName("time") val time: String,
    @SerializedName("location") val location: String? = null,
    @SerializedName("image") val image: Painter? = null
)