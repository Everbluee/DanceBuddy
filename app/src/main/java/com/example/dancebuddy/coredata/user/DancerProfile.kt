package com.example.dancebuddy.coredata.user

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.time.Instant

@Serializable
data class DancerProfile(
    @SerialName("id") override val id: Int,
    @SerialName("email") override val email: String,
    @SerialName("password") override val password: String,
    @SerialName("image") override val image: String? = null,
    @Serializable(with = InstantSerializer::class)
    @SerialName("date_joined") override val dateJoined: Instant,
    @SerialName("first_name") override val firstName: String,
    @SerialName("last_name") override val lastName: String,
    @SerialName("username") override val username: String,
    @SerialName("phone_number") override val phoneNumber: String? = null
) : UserProfile