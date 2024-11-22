package com.example.dancebuddy.coredata

import androidx.compose.ui.graphics.painter.Painter
import com.google.gson.annotations.SerializedName

data class DancerProfile(
    @SerializedName("id") override val id: Int,
    @SerializedName("name") override val name: String,
    @SerializedName("surname") override val surname: String,
    @SerializedName("email") override val email: String,
    @SerializedName("password") override val password: String,
    @SerializedName("image") override val image: Painter? = null,
) : UserProfile