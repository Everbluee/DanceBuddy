package com.example.dancebuddy.coredata

import java.time.LocalDateTime

object TemplateData {
    private const val DATE = "2000-01-01"
    private const val TIME = "12:00:00"
    private val danceClass = DanceClass("HipHop (Advanced)", "" ,"Monday, Wednesday", TIME)
    private val event = Event("Warsztaty", "Warsztaty taneczne z A. Golcem", DATE, TIME)
    private val notification =
        Notification("Zmiana instruktora",
            "Zmiana instruktora prowadządzego: z R*** T*** na P*** K***" +
                    "\nZajęcia: HipHop (Advanced) - poniedziałek",
            DATE, TIME)
    private val profile = DancerProfile(
        id = 0,
        firstName = "Name",
        lastName = "Surname",
        email = "user.name@gmail.com",
        password = "password",
        dateJoined = LocalDateTime.now(),
        username = "User0"
    )

    fun getClass() = danceClass
    fun getEvent() = event
    fun getNotification() = notification
    fun getProfile() = profile
}