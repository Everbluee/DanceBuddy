package com.example.dancebuddy.coredata

import com.google.gson.annotations.SerializedName

data class DanceClass(
    @SerializedName("title") val title: String,
    @SerializedName("description") val description: String = "",
    @SerializedName("date") val date: String,
    @SerializedName("time") val time: String
)