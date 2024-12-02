package com.example.dancebuddy.coredata

import androidx.compose.runtime.Composable
import coil.compose.rememberAsyncImagePainter
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Event(
    @SerialName("title") val title: String,
    @SerialName("description") val description: String,
    @SerialName("date") val date: String,
    @SerialName("time") val time: String,
    @SerialName("location") val location: String? = null,
    @SerialName("image") val image: String? = null
) {
    @Composable
    fun getImage() = rememberAsyncImagePainter(model = image)
}