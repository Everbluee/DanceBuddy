package com.example.dancebuddy.coredata

import androidx.compose.ui.graphics.painter.Painter
import com.google.gson.annotations.SerializedName
import java.time.LocalDateTime

data class DancerProfile(
    @SerializedName("id") override val id: Int,
    @SerializedName("email") override val email: String,
    @SerializedName("password") override val password: String,
    @SerializedName("image") override val image: Painter? = null,
    @SerializedName("date_joined") override val dateJoined: LocalDateTime,
    @SerializedName("first_name") override val firstName: String,
    @SerializedName("last_name") override val lastName: String,
    @SerializedName("username") override val username: String,
    @SerializedName("phone_number") override val phoneNumber: String? = null
) : UserProfile {
    fun matchLoggedProfile(username: String, password: String) {}
}