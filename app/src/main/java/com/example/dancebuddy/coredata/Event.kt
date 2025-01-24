package com.example.dancebuddy.coredata

import androidx.compose.runtime.Composable
import coil.compose.rememberAsyncImagePainter
import kotlinx.serialization.Serializable

@Serializable
data class Event(
    val title: String,
    val description: String,
    val date: String,
    val time: String,
    val location: String? = null,
    val image: String? = null
) {
    @Composable
    fun getImage() = rememberAsyncImagePainter(model = image)
}